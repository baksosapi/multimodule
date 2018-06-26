package org.smartregister.bidan.view;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.smartregister.bidan.R;
import org.smartregister.bidan.adapter.ServiceHistoryAdapter;
import org.smartregister.bidan.repository.ResultDetailsRepository;
import org.smartregister.bidan.repository.ResultsRepository;
import org.smartregister.bidan.constant.BidanConstants;

import java.util.Map;

import static org.smartregister.bidan.R.id.TB_REACH_ID;

/**
 * Created by ndegwamartin on 23/11/2017.
 */

public class RenderServiceHistoryCardHelper extends BaseRenderHelper implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String UNION_TABLE_FLAG = "union_table_flag";
    public static final String UNION_FLAG_CONCAT_SEPARATOR = " ||\'_\'|| ";
    private static final Uri CONTENT_URI = Uri.parse("content://com.smartregister.tbr.service.history.provider");
    private static final int SERVICE_HISTORY_LOADER_ID = 0;
    private static String TAG = RenderServiceHistoryCardHelper.class.getCanonicalName();
    private String BASE_ENTITY_ID = "";

    private LoaderManager.LoaderCallbacks<Cursor> loaderManagerCallbackInterface;
    private ListView listView;

    public RenderServiceHistoryCardHelper(Context context, ResultDetailsRepository detailsRepository) {
        super(context, detailsRepository);

    }

    @Override
    public void renderView(final View view, final Map<String, String> metadata) {
        BASE_ENTITY_ID = metadata.get(BidanConstants.KEY._ID);
        loaderManagerCallbackInterface = this;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                listView = view.findViewById(R.id.serviceHistoryListView);
                if (listView != null) {
                    listView.setTag(TB_REACH_ID, metadata.get(BidanConstants.KEY.TBREACH_ID));
                    LoaderManager loaderManager = ((Activity) context).getLoaderManager();
                    if (loaderManager.getLoader(SERVICE_HISTORY_LOADER_ID) != null) {
                        loaderManager.restartLoader(SERVICE_HISTORY_LOADER_ID, null, loaderManagerCallbackInterface);
                    } else {
                        loaderManager.initLoader(SERVICE_HISTORY_LOADER_ID, null, loaderManagerCallbackInterface);
                    }

                }

            }


        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] mProjection = {
                UNION_TABLE_FLAGS.TEST_RESULT + UNION_FLAG_CONCAT_SEPARATOR + ResultsRepository.ID + " _id", //union query hence unique identifier
                ResultsRepository.TYPE,
                ResultsRepository.FORMSUBMISSION_ID,
                ResultsRepository.DATE,
                ResultsRepository.BASE_ENTITY_ID,
                UNION_TABLE_FLAGS.TEST_RESULT + BidanConstants.CHAR.SPACE + RenderServiceHistoryCardHelper.UNION_TABLE_FLAG,
                ResultsRepository.CREATED_AT
        };

        String[] mProjection2 = {
                UNION_TABLE_FLAGS.SCREENING + UNION_FLAG_CONCAT_SEPARATOR + ECClientRepository.ID + " _id", //union query hence unique identifier
                ECClientRepository.EVENT_TYPE,
                ECClientRepository.ID,
                ECClientRepository.FIRST_ENCOUNTER + BidanConstants.CHAR.SPACE + ResultsRepository.DATE,
                ResultsRepository.BASE_ENTITY_ID,
                UNION_TABLE_FLAGS.SCREENING + BidanConstants.CHAR.SPACE + RenderServiceHistoryCardHelper.UNION_TABLE_FLAG,
                ECClientRepository.BASELINE + " " + ResultsRepository.CREATED_AT
        };

        return new CursorLoader(context, CONTENT_URI,
                mProjection, " WHERE " + ResultsRepository.BASE_ENTITY_ID + "='" + BASE_ENTITY_ID + "'", mProjection2,
                RenderServiceHistoryCardHelper.UNION_TABLE_FLAG + " DESC, " + ResultsRepository.DATE + " DESC, " + ResultsRepository.CREATED_AT + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
            cursor.moveToFirst();
            ServiceHistoryAdapter adapter = new ServiceHistoryAdapter(context, cursor, 0);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Overridden
    }

    public class ECClientRepository {
        public static final String ID = "id";
        public static final String TABLE_NAME = "ec_patient";
        public static final String FIRST_ENCOUNTER = BidanConstants.KEY.FIRST_ENCOUNTER;
        public static final String BASELINE = "baseline";
        public static final String DIAGNOSIS_DATE = "diagnosis_date";
        public static final String EVENT_TYPE = "eventType";
    }

    public class UNION_TABLE_FLAGS {
        public static final String SCREENING = "0";
        public static final String DIAGNOSIS = "1";
        public static final String TEST_RESULT = "2";
    }

    public class FORM_TITLE {
        public static final String SCREENING = "Screening";
        public static final String DIAGNOSIS = "Diagnosis";
    }
}
