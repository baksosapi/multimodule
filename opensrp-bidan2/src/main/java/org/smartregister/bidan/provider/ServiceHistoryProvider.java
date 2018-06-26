package org.smartregister.bidan.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.sqlcipher.database.SQLiteQueryBuilder;

import org.smartregister.bidan.application.BidanApplication;
import org.smartregister.bidan.view.RenderServiceHistoryCardHelper;
import org.smartregister.bidan.repository.ResultsRepository;
import org.smartregister.bidan.constant.BidanConstants;

import static org.smartregister.bidan.view.RenderServiceHistoryCardHelper.UNION_FLAG_CONCAT_SEPARATOR;

/**
 * Created by ndegwamartin on 19/01/2018.
 */

public class ServiceHistoryProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] projection2, @Nullable String sortOrder) {
        //union query based content provider, using selectArgs param for second projection

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(ResultsRepository.TABLE_NAME);
        String projectionStr = getProjectionString(projection);
        String projectionStrTwo = getProjectionString(projection2);

        String[] subQueries = new String[]{
                "SELECT " + projectionStr + " FROM " + ResultsRepository.TABLE_NAME + selection,
                "SELECT " + projectionStrTwo + " FROM " + RenderServiceHistoryCardHelper.ECClientRepository.TABLE_NAME + " p INNER JOIN event e ON e.baseEntityId=p.base_entity_id " + selection + "  AND e.eventType in (\"Screening\", \"positive TB patient\",\"Contact Screening\") ",
                "SELECT " + getDiagnosisFormProjection() + " FROM " + RenderServiceHistoryCardHelper.ECClientRepository.TABLE_NAME + selection + " AND diagnosis_date IS NOT NULL AND diagnosis_date != ''"};
        String sql = builder.buildUnionQuery(subQueries, sortOrder, null);

        return BidanApplication.getInstance().getResultDetailsRepository().getReadableDatabase().rawQuery(sql, null);

    }

    private String getDiagnosisFormProjection() {
        String[] mProjection3 = {RenderServiceHistoryCardHelper.UNION_TABLE_FLAGS.DIAGNOSIS + UNION_FLAG_CONCAT_SEPARATOR + RenderServiceHistoryCardHelper.ECClientRepository.ID + " _id", //union query hence unique identifier
                "\"" + RenderServiceHistoryCardHelper.FORM_TITLE.DIAGNOSIS + "\"",
                RenderServiceHistoryCardHelper.ECClientRepository.ID,
                RenderServiceHistoryCardHelper.ECClientRepository.DIAGNOSIS_DATE + BidanConstants.CHAR.SPACE + ResultsRepository.DATE,
                ResultsRepository.BASE_ENTITY_ID,
                RenderServiceHistoryCardHelper.UNION_TABLE_FLAGS.DIAGNOSIS + BidanConstants.CHAR.SPACE + RenderServiceHistoryCardHelper.UNION_TABLE_FLAG,
                RenderServiceHistoryCardHelper.ECClientRepository.BASELINE + " " + ResultsRepository.CREATED_AT
        };
        return getProjectionString(mProjection3);


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private String getProjectionString(String[] projection) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : projection)
            stringBuilder.append(s).append(",");

        String projectionStr = stringBuilder.toString();
        projectionStr = projectionStr.substring(0,
                projectionStr.length() - 1);
        return projectionStr;

    }
}
