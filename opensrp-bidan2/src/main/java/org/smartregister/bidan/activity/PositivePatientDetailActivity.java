package org.smartregister.bidan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import org.smartregister.bidan.R;
import org.smartregister.bidan.fragment.PositivePatientDetailsFragment;

import java.util.HashMap;

/**
 * Created by ndegwamartin on 09/10/2017.
 */

public class PositivePatientDetailActivity extends BasePatientDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_detail_positive_settings, menu);
        return true;
    }

    @Override
    protected Fragment getDetailFragment() {

        PositivePatientDetailsFragment fragment = new PositivePatientDetailsFragment();
        patientDetails = (HashMap<String, String>) getIntent().getSerializableExtra(org.smartregister.bidan.constant.BidanConstants.INTENT_KEY.PATIENT_DETAIL_MAP);
        fragment.setPatientDetails(patientDetails);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.treatmentInitiationForm:
                patientDetails = (HashMap<String, String>) getIntent().getSerializableExtra(org.smartregister.bidan.constant.BidanConstants.INTENT_KEY.PATIENT_DETAIL_MAP);
                formOverridesHelper.setPatientDetails(patientDetails);
                startFormActivity(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.TREATMENT_INITIATION, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY._ID), formOverridesHelper.getTreatmentFieldOverrides().getJSONString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
