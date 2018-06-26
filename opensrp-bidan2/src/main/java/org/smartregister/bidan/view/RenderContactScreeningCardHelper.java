package org.smartregister.bidan.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.sqlcipher.database.SQLiteDatabase;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.bidan.R;
import org.smartregister.bidan.activity.BasePatientDetailActivity;
import org.smartregister.bidan.application.BidanApplication;
import org.smartregister.bidan.database.helper.FormOverridesHelper;
import org.smartregister.bidan.database.model.Contact;
import org.smartregister.bidan.repository.ResultsRepository;
import org.smartregister.bidan.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.smartregister.bidan.constant.BidanConstants.ScreenStage;
import static org.smartregister.bidan.constant.BidanConstants.CONTACT_TABLE_NAME;
import static org.smartregister.bidan.constant.BidanConstants.KEY;
import static org.smartregister.bidan.constant.BidanConstants.PATIENT_TABLE_NAME;

/**
 * Created by ndegwamartin on 23/11/2017.
 */

public class RenderContactScreeningCardHelper extends BaseRenderHelper {

    private static String TAG = RenderContactScreeningCardHelper.class.getCanonicalName();

    public RenderContactScreeningCardHelper(Context context, ResultsRepository detailsRepository) {
        super(context, detailsRepository);
    }

    @Override
    public void renderView(final View view, final Map<String, String> metadata) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {

                FrameLayout contactViewTemplate = view.findViewById(R.id.clientContactFrameLayout);
                if (contactViewTemplate != null) {
                    ViewGroup contactsHolderView = view.findViewById(R.id.contactScreeningViewContactsHolder);
                    contactViewTemplate.setVisibility(View.GONE);
                    contactsHolderView.removeAllViews();
                    contactsHolderView.addView(contactViewTemplate);//Reinstate default guy for next reuse

                    final List<Contact> contacts = getContacts(metadata.get(KEY.BASE_ENTITY_ID));
                    FrameLayout contactView;

                    for (int i = 0; i < contacts.size(); i++) {
                        ScreenContactViewHelper screenContactViewHelper = new ScreenContactViewHelper(context, contactViewTemplate, contacts.get(i));
                        contactView = screenContactViewHelper.getScreenContactView();
                        contactView.setTag(R.id.CONTACT, contacts.get(i));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Contact contact = (Contact) view.getTag(R.id.CONTACT);
                                if (contact != null) {
                                    if (contact.getStage().equals(ScreenStage.NOT_SCREENED)) {
                                        Map contactDetails = getCommonPersonObjectDetails(contact.getBaseEntityId(), CONTACT_TABLE_NAME);
                                        FormOverridesHelper formOverridesHelper = new FormOverridesHelper(contactDetails);
                                        ((BasePatientDetailActivity) context).startFormActivity(org.smartregister.bidan.constant.BidanConstants.ENKETO_FORMS.CONTACT_SCREENING, contact.getBaseEntityId(), formOverridesHelper.getContactScreeningFieldOverrides().getJSONString());
                                    } else {
                                        Map contactDetails = getCommonPersonObjectDetails(contact.getBaseEntityId(), PATIENT_TABLE_NAME);
                                        contactDetails.put(org.smartregister.bidan.constant.BidanConstants.KEY._ID, contact.getBaseEntityId());
                                        ((BasePatientDetailActivity) context).goToPatientDetailActivity(
                                                contact.getStage(), contactDetails);
                                    }
                                }
                            }
                        });

                        contactsHolderView.addView(contactView);

                    }
                } else {
                    Log.e(TAG, "No Frame Layout contactViewTemplate found");
                }
            }

        });

    }

    private List<Contact> getContacts(String baseEntityId) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT c." + KEY.FIRST_NAME +
                " ,c." + KEY.LAST_NAME +
                " ,c." + KEY.GENDER +
                " ,c." + KEY.DOB +
                ", c. " + KEY.PROGRAM_ID +
                ", c. " + KEY.BASE_ENTITY_ID +
                ", p. " + KEY.PRESUMPTIVE +
                ", p. " + KEY.CONFIRMED_TB +
                ", p. " + KEY.TREATMENT_INITIATION_DATE +
                ", '0' as " + KEY.INDEX +
                " FROM " + CONTACT_TABLE_NAME + " c " +
                " LEFT JOIN " + PATIENT_TABLE_NAME + " p ON c." + KEY.BASE_ENTITY_ID + "=p." + KEY.BASE_ENTITY_ID +
                " WHERE c." + KEY.RELATIONAL_ID + "= ? UNION " +
                "SELECT p." + KEY.FIRST_NAME +
                " ,p." + KEY.LAST_NAME +
                " ,p." + KEY.GENDER +
                " ,p." + KEY.DOB +
                ", p. " + KEY.PROGRAM_ID +
                ", p. " + KEY.BASE_ENTITY_ID +
                ", p. " + KEY.PRESUMPTIVE +
                ", p. " + KEY.CONFIRMED_TB +
                ", p. " + KEY.TREATMENT_INITIATION_DATE +
                ", '1' as " + KEY.INDEX +
                " FROM " + CONTACT_TABLE_NAME + " c " +
                " JOIN " + PATIENT_TABLE_NAME + " p ON c." + KEY.RELATIONAL_ID + "=p." + KEY.BASE_ENTITY_ID +
                " WHERE c." + KEY.BASE_ENTITY_ID + "= ?";

        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery(sql, new String[]{baseEntityId, baseEntityId});
            while (cursor.moveToNext()) {
                Contact c = new Contact();
                c.setBaseEntityId(cursor.getString(cursor.getColumnIndex(KEY.BASE_ENTITY_ID)));
                c.setFirstName(cursor.getString(cursor.getColumnIndex(KEY.FIRST_NAME)));
                c.setLastName(cursor.getString(cursor.getColumnIndex(KEY.LAST_NAME)));
                c.setGender(cursor.getString(cursor.getColumnIndex(KEY.GENDER)));
                c.setContactId(cursor.getString(cursor.getColumnIndex(KEY.PROGRAM_ID)));
                c.setAge(Utils.getFormattedAgeString(cursor.getString(cursor.getColumnIndex(KEY.DOB))));
                c.setIndex(cursor.getInt(cursor.getColumnIndex(KEY.INDEX)) == 1);
                String presumptive = cursor.getString(cursor.getColumnIndex(KEY.PRESUMPTIVE));
                if (StringUtils.isNotEmpty(cursor.getString(cursor.getColumnIndex(KEY.TREATMENT_INITIATION_DATE))))
                    c.setStage(ScreenStage.IN_TREATMENT);
                else if (StringUtils.isNotEmpty(cursor.getString(cursor.getColumnIndex(KEY.CONFIRMED_TB))))
                    c.setStage(ScreenStage.POSITIVE);
                else if (StringUtils.isNotEmpty(presumptive) && presumptive.equalsIgnoreCase("yes"))
                    c.setStage(ScreenStage.PRESUMPTIVE);
                else if (StringUtils.isNotEmpty(presumptive) && presumptive.equalsIgnoreCase("no"))
                    c.setStage(ScreenStage.SCREENED);
                else
                    c.setStage(ScreenStage.NOT_SCREENED);
                contacts.add(c);
            }

        } catch (Exception e) {
            Log.e(TAG, "error occcured fetching Contacts: ", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return contacts;
    }

    private SQLiteDatabase getReadableDatabase() {
        return BidanApplication.getInstance().getRepository().getReadableDatabase();
    }

    private Map getCommonPersonObjectDetails(String baseEntityId, String tableName) {
        Cursor cursor = null;
        Map details = new HashMap();
        try {
            cursor = getReadableDatabase().query(tableName, null, KEY.BASE_ENTITY_ID + "=?"
                    , new String[]{baseEntityId}, null, null, null);
            if (cursor.moveToFirst())
                details = BidanApplication.getInstance().getContext().commonrepository(tableName)
                        .sqliteRowToMap(cursor);
        } catch (Exception e) {
            Log.e(TAG, "error occcured fetching CommonPersonObject: ", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return details;
    }
}
