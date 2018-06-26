package org.smartregister.bidan.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.domain.FetchStatus;
import org.smartregister.enketo.adapter.pager.EnketoRegisterPagerAdapter;
import org.smartregister.enketo.listener.DisplayFormListener;
import org.smartregister.enketo.view.fragment.DisplayFormFragment;
import org.smartregister.provider.SmartRegisterClientsProvider;
import org.smartregister.bidan.R;
import org.smartregister.bidan.event.EnketoFormSaveCompleteEvent;
import org.smartregister.bidan.event.ShowProgressDialogEvent;
import org.smartregister.bidan.event.SyncEvent;
import org.smartregister.bidan.fragment.BaseRegisterFragment;
import org.smartregister.view.activity.SecuredNativeSmartRegisterActivity;
import org.smartregister.view.viewpager.OpenSRPViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.EnketoFormUtils;

import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.CHEST_XRAY;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.CULTURE;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.DIAGNOSIS;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.GENE_XPERT;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.SMEAR;


/**
 * Created by samuelgithengi on 10/30/17.
 */

public abstract class BaseRegisterActivity extends SecuredNativeSmartRegisterActivity implements DisplayFormListener {

    public static final String TAG = "BaseRegisterActivity";

    public static String TOOLBAR_TITLE = "org.smartregister.tbr.activity.toolbarTitle";

    private ProgressDialog progressDialog;

    @Bind(R.id.view_pager)
    protected OpenSRPViewPager mPager;
    protected FragmentPagerAdapter mPagerAdapter;
    protected int currentPage;

    protected List<String> formNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_register);
        ButterKnife.bind(this);
        formNames = this.buildFormNameList();
        Fragment mBaseFragment = getRegisterFragment();

        // Instantiate a ViewPager and a PagerAdapter.
        mPagerAdapter = new EnketoRegisterPagerAdapter(getSupportFragmentManager(), formNames.toArray(new String[formNames.size()]), mBaseFragment);
        mPager.setOffscreenPageLimit(formNames.size());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
        });
        initializeEnketoFormFragment(formNames.get(0), null, null, false);
        //mPager.setCurrentItem(0, false);
    }

    protected abstract Fragment getRegisterFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        processMenuConfigurations(menu);
        return true;
    }

    private void processMenuConfigurations(Menu menu) {
        if (getViewIdentifiers().isEmpty())
            return;
        ViewConfiguration viewConfiguration = ConfigurableViewsLibrary.getInstance()
                .getConfigurableViewsHelper().getViewConfiguration(getViewIdentifiers().get(0));
        if (viewConfiguration == null)
            return;
        RegisterConfiguration metadata = (RegisterConfiguration) viewConfiguration.getMetadata();
        menu.findItem(R.id.advancedSearch).setVisible(metadata.isEnableAdvancedSearch());
        menu.findItem(R.id.sortList).setVisible(metadata.isEnableSortList());
        menu.findItem(R.id.filterList).setVisible(metadata.isEnableFilterList());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return null;
    }

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {
        return null;
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        return null;
    }

    @Override
    protected void setupViews() {//Implement Abstract Method
    }

    @Override
    protected void onResumption() {
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().registerViewConfigurations(getViewIdentifiers());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onInitialization() {//Implement Abstract Method
    }

    @Override
    public void startRegistration() {//Implement Abstract Method
    }

    private Fragment findFragmentByPosition(int position) {
        return getSupportFragmentManager().findFragmentByTag("android:switcher:" + mPager.getId() + ":" + mPagerAdapter.getItemId(position));
    }

    public void refreshList(final FetchStatus fetchStatus) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            BaseRegisterFragment registerFragment = (BaseRegisterFragment) findFragmentByPosition(0);
            if (registerFragment != null && fetchStatus.equals(FetchStatus.fetched)) {
                registerFragment.refreshListView();
            }
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    BaseRegisterFragment registerFragment = (BaseRegisterFragment) findFragmentByPosition(0);
                    if (registerFragment != null && fetchStatus.equals(FetchStatus.fetched)) {
                        registerFragment.refreshListView();
                    }
                }
            });
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showProgressDialog(ShowProgressDialogEvent showProgressDialogEvent) {
        if (showProgressDialogEvent != null)
            showProgressDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void saveFormSubmissionComplete(EnketoFormSaveCompleteEvent enketoFormSaveCompleteEvent) {
        if (enketoFormSaveCompleteEvent != null) {
            refreshList(FetchStatus.fetched);
            hideProgressDialog();
            switchToBaseFragment();
        }
    }

    @Override
    public void onFormClosed(String recordId, String formName) {
        Toast.makeText(this, formName + " closed", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
        switchToBaseFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(SyncEvent syncEvent) {
        if (syncEvent != null && syncEvent.getFetchStatus().equals(FetchStatus.fetched))
            refreshList(FetchStatus.fetched);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.saving_dialog_title));
        progressDialog.setMessage(getString(R.string.please_wait_message));
        if (!isFinishing())
            progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void startFormActivity(String formName, String entityId, String metaData) {
        initializeEnketoFormFragment(formName, entityId, metaData, true);
    }


    public void initializeEnketoFormFragment(String formName, String entityId, String metaData, boolean displayForm) {
        try {
            int formIndex = formNames.indexOf(formName) + 1; // add the offset
            if (entityId != null || metaData != null) {
                String data = EnketoFormUtils.getInstance(getApplicationContext()).generateXMLInputForFormWithEntityId(entityId, formName, metaData);
                DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(formIndex);
                if (displayFormFragment != null) {
                    displayFormFragment.setFormData(data);
                    displayFormFragment.setRecordId(entityId);
                    displayFormFragment.setFieldOverides(metaData);
                    displayFormFragment.setListener(this);
                    displayFormFragment.setResize(false);
                }
            }

            if (displayForm)
                mPager.setCurrentItem(formIndex, false); //Don't animate the view on orientation change the view disapears

        } catch (Exception e) {
            Log.e(TAG, "startFormActivity: ", e);
        }

    }

    private DisplayFormFragment getDisplayFormFragmentAtIndex(int index) {
        return (DisplayFormFragment) findFragmentByPosition(index);
    }

    protected List<String> buildFormNameList() {
        List<String> formNames = new ArrayList<String>();
        formNames.add(GENE_XPERT);
        formNames.add(SMEAR);
        formNames.add(CHEST_XRAY);
        formNames.add(CULTURE);
        formNames.add(DIAGNOSIS);

        formNames.add(org.smartregister.bidan.constant.BidanConstants.FORM.NEW_PATIENT_REGISTRATION);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.TREATMENT_INITIATION);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.FORM.CONTACT_SCREENING);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.FOLLOWUP_VISIT);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.ADD_TB_CONTACT);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.FORM.REMOVE_PATIENT);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.FORM.TREATMENT_OUTCOME);
        formNames.add(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.ADD_POSITIVE_PATIENT);
        return formNames;
    }

    public void switchToBaseFragment() {
        final int prevPageIndex = currentPage;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(0, false);
                DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(prevPageIndex);
                if (displayFormFragment != null) {
                    displayFormFragment.hideTranslucentProgressDialog();
                    displayFormFragment.setFormData(null);
                    displayFormFragment.setRecordId(null);
                }
            }
        });

    }

    @Override
    public void saveFormSubmission(String formSubmision, String id, String formName, JSONObject fieldOverrides) {
        try {
            EnketoFormUtils enketoFormUtils = EnketoFormUtils.getInstance(getApplicationContext());
            enketoFormUtils.generateFormSubmisionFromXMLString(id, formSubmision, formName, fieldOverrides);
        } catch (Exception e) {
            Log.i(TAG, "saveFormSubmission: ", e);
            switchToBaseFragment();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().unregisterViewConfiguration(getViewIdentifiers());
    }

    public abstract List<String> getViewIdentifiers();

    @Override
    public void onBackPressed() {
        if (currentPage != 0) {
            new AlertDialog.Builder(this, R.style.TbrAlertDialog)
                    .setMessage(R.string.form_back_confirm_dialog_message)
                    .setTitle(R.string.form_back_confirm_dialog_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.no_button_label,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Do nothing, remain on Enketo Form Fragment
                                }
                            })
                    .setNegativeButton(R.string.yes_button_label,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    switchToBaseFragment();
                                }
                            })
                    .show();
        } else {
            super.onBackPressed(); // allow back key only if we are
        }
    }

}
