package org.smartregister.bidan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import org.smartregister.bidan.R;
import org.smartregister.bidan.fragment.PresumptivePatientDetailsFragment;
import org.smartregister.bidan.constant.BidanConstants;

import java.util.HashMap;

/**
 * Created by ndegwamartin on 09/10/2017.
 */

public class PresumptivePatientDetailActivity extends BasePatientDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Fragment getDetailFragment() {
        PresumptivePatientDetailsFragment mBaseFragment = new PresumptivePatientDetailsFragment();
        patientDetails = (HashMap<String, String>) getIntent().getSerializableExtra(BidanConstants.INTENT_KEY.PATIENT_DETAIL_MAP);
        mBaseFragment.setPatientDetails(patientDetails);
        return mBaseFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_detail_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tbDiagnosisForm:
                patientDetails = (HashMap<String, String>) getIntent().getSerializableExtra(BidanConstants.INTENT_KEY.PATIENT_DETAIL_MAP);
                formOverridesHelper.setPatientDetails(patientDetails);
                startFormActivity(BidanConstants.FORM.DIAGNOSIS, patientDetails.get(BidanConstants.KEY._ID), formOverridesHelper.getFieldOverrides().getJSONString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
