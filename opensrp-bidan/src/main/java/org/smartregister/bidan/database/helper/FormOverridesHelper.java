package org.smartregister.bidan.database.helper;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.smartregister.domain.form.FieldOverrides;
import org.smartregister.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ndegwamartin on 27/01/2018.
 */

public class FormOverridesHelper {

    private String TAG = FormOverridesHelper.class.getCanonicalName();

    private Map<String, String> patientDetails;

    public FormOverridesHelper(Map<String, String> patientDetails) {
        this.patientDetails = patientDetails;
    }

    public void setPatientDetails(Map<String, String> patientDetails) {
        this.patientDetails = patientDetails;
    }

    public Map populateFieldOverrides() {
        Map fields = new HashMap();
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.PARTICIPANT_ID, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.PARTICIPANT_ID));
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.FIRST_NAME, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.FIRST_NAME));
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.LAST_NAME, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.LAST_NAME));
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.PROGRAM_ID, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.PROGRAM_ID));
        return fields;
    }

    public FieldOverrides getFieldOverrides() {
        Map fields = populateFieldOverrides();
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getFollowUpFieldOverrides() {
        Map fields = populateFieldOverrides();
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.TREATMENT_INITIATION_DATE, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.TREATMENT_INITIATION_DATE));
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getTreatmentFieldOverrides() {
        Map fields = populateFieldOverrides();
        String gender = patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.GENDER);
        if (gender != null) {
            fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.GENDER, gender);
        }
        String dobString = patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.DOB);
        String age = "";
        if (StringUtils.isNotBlank(dobString)) {
            try {
                DateTime birthDateTime = new DateTime(dobString);
                String duration = DateUtil.getDuration(birthDateTime);
                if (duration != null) {
                    age = duration.substring(0, duration.length() - 1);
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
        }
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.AGE, age);
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getAddContactFieldOverrides() {
        Map fields = new HashMap();
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.PARTICIPANT_ID, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY.PARTICIPANT_ID));
        fields.put(org.smartregister.bidan.constant.BidanConstants.KEY.PARENT_ENTITY_ID, patientDetails.get(org.smartregister.bidan.constant.BidanConstants.KEY._ID));
        return new FieldOverrides(new JSONObject(fields).toString());
    }

    public FieldOverrides getContactScreeningFieldOverrides() {
        Map fields = populateFieldOverrides();
        fields.remove(org.smartregister.bidan.constant.BidanConstants.KEY.PARTICIPANT_ID);
        return new FieldOverrides(new JSONObject(fields).toString());
    }
}
