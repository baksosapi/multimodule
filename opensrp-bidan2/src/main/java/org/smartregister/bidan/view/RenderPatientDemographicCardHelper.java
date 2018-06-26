package org.smartregister.bidan.view;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;
import org.smartregister.bidan.R;
import org.smartregister.bidan.application.BidanApplication;
import org.smartregister.bidan.repository.ResultDetailsRepository;
import org.smartregister.bidan.constant.BidanConstants;
import org.smartregister.bidan.util.Utils;

import java.util.Map;

/**
 * Created by ndegwamartin on 23/11/2017.
 */

public class RenderPatientDemographicCardHelper extends BaseRenderHelper {
    private static final String TAG = RenderPatientDemographicCardHelper.class.getCanonicalName();

    public RenderPatientDemographicCardHelper(Context context, ResultDetailsRepository detailsRepository) {
        super(context, detailsRepository);
    }

    @Override
    public void renderView(final View view, final Map<String, String> patientDetails) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                try {
                    if (view.getTag(R.id.VIEW_CONFIGURATION_ID) == BidanConstants.CONFIGURATION.PATIENT_DETAILS_INTREATMENT) {
                        Map<String, String> details = BidanApplication.getInstance().getContext().detailsRepository().getAllDetailsForClient(patientDetails.get(BidanConstants.KEY._ID));

                        TextView patientTypeTextView = view.findViewById(R.id.patientTypeTextView);
                        if (details.get(BidanConstants.KEY.PATIENT_TYPE) != null && !details.get(BidanConstants.KEY.PATIENT_TYPE).isEmpty()) {
                            patientTypeTextView.setText(WordUtils.capitalizeFully(details.get(BidanConstants.KEY.PATIENT_TYPE).replace(BidanConstants.CHAR.UNDERSCORE, BidanConstants.CHAR.SPACE)));
                            patientTypeTextView.setVisibility(View.VISIBLE);
                        }
                        if (details.get(BidanConstants.KEY.SITE_OF_DISEASE) != null && !details.get(BidanConstants.KEY.SITE_OF_DISEASE).isEmpty()) {
                            TextView siteOfDiseaseTextView = view.findViewById(R.id.siteOfDiseaseTextView);
                            siteOfDiseaseTextView.setText(Utils.getTBTypeByCode(details.get(BidanConstants.KEY.SITE_OF_DISEASE)));
                            siteOfDiseaseTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    TextView tbReachIdTextView = view.findViewById(R.id.tbReachIdTextView);
                    tbReachIdTextView.setText(Utils.formatIdentifier(patientDetails.get(BidanConstants.KEY.PARTICIPANT_ID)));

                    TextView clientAgeTextView = view.findViewById(R.id.clientAgeTextView);
                    String dobString = patientDetails.get(BidanConstants.KEY.DOB);
                    String formattedAge = Utils.getFormattedAgeString(dobString);
                    clientAgeTextView.setText(formattedAge);

                    TextView clientNameTextView = view.findViewById(R.id.clientNameTextView);
                    String fullName = patientDetails.get(BidanConstants.KEY.FIRST_NAME) + " " + patientDetails.get(BidanConstants.KEY.LAST_NAME);
                    clientNameTextView.setText(fullName);
                    TextView clientGenderTextView = view.findViewById(R.id.clientGenderTextView);
                    clientGenderTextView.setText(WordUtils.capitalize(patientDetails.get(BidanConstants.KEY.GENDER)));

                    TextView clientInitalsTextView = view.findViewById(R.id.clientInitalsTextView);
                    clientInitalsTextView.setText(Utils.getShortInitials(fullName));

                    if (patientDetails.get(BidanConstants.KEY.GENDER).equals(BidanConstants.GENDER.MALE)) {
                        clientInitalsTextView.setBackgroundColor(context.getResources().getColor(R.color.male_light_blue));
                        clientInitalsTextView.setTextColor(context.getResources().getColor(R.color.male_blue));

                    } else if (patientDetails.get(BidanConstants.KEY.GENDER).equals(BidanConstants.GENDER.FEMALE)) {
                        clientInitalsTextView.setBackgroundColor(context.getResources().getColor(R.color.female_light_pink));
                        clientInitalsTextView.setTextColor(context.getResources().getColor(R.color.female_pink));
                    } else {
                        clientInitalsTextView.setBackgroundColor(context.getResources().getColor(R.color.gender_neutral_light_green));
                        clientInitalsTextView.setTextColor(context.getResources().getColor(R.color.gender_neutral_green));
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

        });
    }
}
