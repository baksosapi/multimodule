package org.smartregister.bidan.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import org.smartregister.bidan.R;
import org.smartregister.bidan.fragment.InTreatmentPatientRegisterFragment;

import java.util.Arrays;
import java.util.List;

import static org.smartregister.util.JsonFormUtils.generateRandomUUIDString;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.ADD_IN_TREATMENT_PATIENT;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.FOLLOWUP_VISIT;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.COMMON_REGISTER_HEADER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.COMMON_REGISTER_ROW;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.INTREATMENT_REGISTER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.INTREATMENT_REGISTER_HEADER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.INTREATMENT_REGISTER_ROW;

/**
 * Created by samuelgithengi on 12/5/17.
 */

public class InTreatmentPatientRegisterActivity extends BaseRegisterActivity {
    @Override
    protected Fragment getRegisterFragment() {
        return new InTreatmentPatientRegisterFragment();
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Arrays.asList(INTREATMENT_REGISTER, INTREATMENT_REGISTER_HEADER, INTREATMENT_REGISTER_ROW, COMMON_REGISTER_HEADER, COMMON_REGISTER_ROW);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewPatient:
                String entityId = generateRandomUUIDString();
                startFormActivity(ADD_IN_TREATMENT_PATIENT, entityId, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected List<String> buildFormNameList() {
        formNames = super.buildFormNameList();
        formNames.add(FOLLOWUP_VISIT);
        formNames.add(ADD_IN_TREATMENT_PATIENT);
        return formNames;
    }
}
