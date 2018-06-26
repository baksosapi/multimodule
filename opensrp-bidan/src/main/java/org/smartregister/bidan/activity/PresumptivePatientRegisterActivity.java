package org.smartregister.bidan.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;
import org.smartregister.bidan.R;
import org.smartregister.bidan.fragment.PresumptivePatientRegisterFragment;

import java.util.Arrays;
import java.util.List;

import static org.smartregister.util.JsonFormUtils.generateRandomUUIDString;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.DIAGNOSIS;
import static org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.SCREENING_FORM;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.COMMON_REGISTER_HEADER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.COMMON_REGISTER_ROW;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.PRESUMPTIVE_REGISTER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.PRESUMPTIVE_REGISTER_HEADER;
import static org.smartregister.bidan.constant.BidanConstants.VIEW_CONFIGS.PRESUMPTIVE_REGISTER_ROW;

/**
 * Created by samuelgithengi on 10/30/17.
 */

public class PresumptivePatientRegisterActivity extends BaseRegisterActivity {

    @Override
    protected Fragment getRegisterFragment() {
        return new PresumptivePatientRegisterFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewPatient:
                String entityId = generateRandomUUIDString();
                startFormActivity(SCREENING_FORM, entityId, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void savePartialFormData(String formData, String id, String formName, JSONObject fieldOverrides) {
        Toast.makeText(this, formName + " partially submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Arrays.asList(PRESUMPTIVE_REGISTER, PRESUMPTIVE_REGISTER_HEADER, PRESUMPTIVE_REGISTER_ROW, COMMON_REGISTER_HEADER, COMMON_REGISTER_ROW);
    }

    @Override
    protected List<String> buildFormNameList() {
        formNames = super.buildFormNameList();
        formNames.add(0, SCREENING_FORM);
        formNames.add(DIAGNOSIS);
        return formNames;
    }
}
