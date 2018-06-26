package org.smartregister.bidan.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.bidan.application.BidanApplication;
import org.smartregister.clientandeventmodel.populateform.PopulateEnketoFormUtils;
import org.smartregister.domain.form.FieldOverrides;
import org.smartregister.bidan.R;
import org.smartregister.bidan.activity.BasePatientDetailActivity;
import org.smartregister.bidan.view.RenderServiceHistoryCardHelper;
import org.smartregister.bidan.repository.ResultsRepository;
import org.smartregister.bidan.util.Utils;

/**
 * Created by ndegwamartin on 20/11/2017.
 */

public class ServiceHistoryAdapter extends CursorAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater inflater;

    public ServiceHistoryAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.mContext = context;

        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onClick(View view) {
        View formView = view.findViewById(R.id.formNameTextView);

        int formIdentifier = getFormIdentifierFromName(formView.getTag(R.id.FORM_NAME).toString());
        String formSubmissionId = formView.getTag(R.id.FORM_SUBMISSION_ID).toString();
        String baseEntityId = formView.getTag(R.id.BASE_ENTITY_ID).toString();
        PopulateEnketoFormUtils enketoFormUtils = PopulateEnketoFormUtils.getInstance(mContext, BidanApplication.getInstance().getEventClientRepository());
        String form;
        switch (formIdentifier) {
            case R.id.result_gene_xpert:
                form = org.smartregister.bidan.constant.BidanConstants.FORM.RESULT_GENE_EXPERT;
                break;
            case R.id.result_smear:
                form = org.smartregister.bidan.constant.BidanConstants.FORM.RESULT_SMEAR;
                break;
            case R.id.result_chest_xray:
                form = org.smartregister.bidan.constant.BidanConstants.FORM.RESULT_CHEST_XRAY;
                break;
            case R.id.result_culture:
                form = org.smartregister.bidan.constant.BidanConstants.FORM.RESULT_CULTURE;
                break;
            case R.id.addNewPatient:
                formSubmissionId = null;
                form = org.smartregister.bidan.constant.BidanConstants.FORM.NEW_PATIENT_REGISTRATION;
                break;
            case R.id.tbDiagnosisForm:
                formSubmissionId = null;
                form = org.smartregister.bidan.constant.BidanConstants.FORM.DIAGNOSIS;
                break;
            case R.id.contact_screening:
                formSubmissionId = null;
                form = org.smartregister.bidan.constant.BidanConstants.FORM.CONTACT_SCREENING;
                break;
            case R.id.POSITIVE_TB_PATIENT:
                formSubmissionId = null;
                form = org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.ADD_POSITIVE_PATIENT;
                break;
            default:
                return;
        }
        FieldOverrides formOverrides = enketoFormUtils.populateFormOverrides(baseEntityId, formSubmissionId, form);
        ((BasePatientDetailActivity) mContext).startFormActivity(form, baseEntityId, formOverrides.getJSONString(), true);

    }

    private int getFormIdentifierFromName(String formName) {
        if (StringUtils.containsIgnoreCase(formName, "gene")) {
            return R.id.result_gene_xpert;
        } else if (StringUtils.containsIgnoreCase(formName, "smear")) {
            return R.id.result_smear;
        } else if (StringUtils.containsIgnoreCase(formName, "x-ray")) {
            return R.id.result_chest_xray;
        } else if (StringUtils.containsIgnoreCase(formName, "culture")) {
            return R.id.result_culture;
        } else if (StringUtils.containsIgnoreCase(formName, "diagnosis")) {
            return R.id.tbDiagnosisForm;
        } else if (StringUtils.equalsIgnoreCase(formName, org.smartregister.bidan.constant.BidanConstants.EVENT.CONTACT_SCREENING)) {
            return R.id.contact_screening;
        } else if (StringUtils.containsIgnoreCase(formName, org.smartregister.bidan.constant.BidanConstants.EVENT.POSITIVE_TB_PATIENT)) {
            return R.id.POSITIVE_TB_PATIENT;
        } else if (StringUtils.containsIgnoreCase(formName, org.smartregister.bidan.constant.BidanConstants.EVENT.SCREENING)) {
            return R.id.addNewPatient;
        } else {
            return 0;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.row_service_history, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dateView = view.findViewById(R.id.formfillDateTextView);
        if (cursor.getString(cursor.getColumnIndex(org.smartregister.bidan.constant.BidanConstants.KEY.DATE)) != null) {
            String date = cursor.getString(cursor.getColumnIndex(RenderServiceHistoryCardHelper.UNION_TABLE_FLAG)).equals(RenderServiceHistoryCardHelper.UNION_TABLE_FLAGS.TEST_RESULT) ? Utils.formatDateFromLong(cursor.getLong(cursor.getColumnIndex(org.smartregister.bidan.constant.BidanConstants.KEY.DATE)), "dd MMM yyyy") : Utils.formatDate(org.smartregister.util.Utils.toDate(cursor.getString(cursor.getColumnIndex(org.smartregister.bidan.constant.BidanConstants.KEY.DATE)), true), "dd MMM yyyy");
            dateView.setText(date);
        }
        TextView formName = view.findViewById(R.id.formNameTextView);
        formName.setText(StringUtils.capitalize(cursor.getString(cursor.getColumnIndex(ResultsRepository.TYPE))));
        formName.setOnClickListener(this);
        formName.setTag(cursor.getString(cursor.getColumnIndex(ResultsRepository.ID)));
        formName.setTag(R.id.FORM_NAME, cursor.getString(cursor.getColumnIndex(ResultsRepository.TYPE)));
        formName.setTag(R.id.FORM_SUBMITTED_DATE, cursor.getString(cursor.getColumnIndex(ResultsRepository.DATE)));
        formName.setTag(R.id.FORM_SUBMISSION_ID, cursor.getString(cursor.getColumnIndex(ResultsRepository.FORMSUBMISSION_ID)));
        formName.setTag(R.id.BASE_ENTITY_ID, cursor.getString(cursor.getColumnIndex(ResultsRepository.BASE_ENTITY_ID)));

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_from_top);
        view.startAnimation(animation);
    }

}