package org.smartregister.bidan.activity;

import android.support.v4.app.Fragment;

import org.smartregister.bidan.fragment.ScreenedPatientDetailsFragment;
import org.smartregister.bidan.constant.BidanConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuelgithengi on 2/1/18.
 */

public class ScreenedPatientDetailActivity extends BasePatientDetailActivity {

    @Override
    protected Fragment getDetailFragment() {
        ScreenedPatientDetailsFragment mBaseFragment = new ScreenedPatientDetailsFragment();
        Map<String, String> patientDetails = (HashMap<String, String>) getIntent().getSerializableExtra(BidanConstants.INTENT_KEY.PATIENT_DETAIL_MAP);
        mBaseFragment.setPatientDetails(patientDetails);
        return mBaseFragment;
    }


}
