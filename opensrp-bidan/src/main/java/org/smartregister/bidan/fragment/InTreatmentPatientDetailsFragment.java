package org.smartregister.bidan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.smartregister.bidan.R;
import org.smartregister.bidan.constant.BidanConstants;

import java.util.Map;

import static org.smartregister.bidan.constant.BidanConstants.INTENT_KEY.REGISTER_TITLE;

/**
 * Created by ndegwamartin on 24/11/2017.
 */


public class InTreatmentPatientDetailsFragment extends BasePatientDetailsFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_intreatment_patient_detail, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(activity.getIntent().getStringExtra(REGISTER_TITLE));
        rootView.setTag(R.id.VIEW_CONFIGURATION_ID, getViewConfigurationIdentifier());
        if (patientDetails != null && patientDetails.containsKey(BidanConstants.KEY._ID)) {
            rootView.setTag(R.id.BASE_ENTITY_ID, patientDetails.get(BidanConstants.KEY._ID));
        }
        setupViews(rootView);
        return rootView;
    }

    @Override
    public void setupViews(View rootView) {
        super.setupViews(rootView);
        processViewConfigurations(rootView);
    }

    @Override
    public void setPatientDetails(Map<String, String> patientDetails) {
        this.patientDetails = patientDetails;
    }

    @Override
    protected String getViewConfigurationIdentifier() {
        return BidanConstants.CONFIGURATION.PATIENT_DETAILS_INTREATMENT;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onResumption() {
        //Overridden method
    }


}
