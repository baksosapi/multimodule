package org.smartregister.bidan.view;

import android.content.Context;
import android.text.style.ForegroundColorSpan;

import org.apache.commons.lang3.text.WordUtils;
import org.smartregister.bidan.R;

import java.util.Map;

import util.TbrSpannableStringBuilder;

/**
 * Created by ndegwamartin on 15/01/2018.
 */

public class TestResultsStringBuilderHelper {
    private Context context;

    TestResultsStringBuilderHelper(Context context) {
        this.context = context;
    }

    public TbrSpannableStringBuilder getSmearResultStringBuilder(Map<String, String> testResults, TbrSpannableStringBuilder stringBuilder) {
        ForegroundColorSpan redForegroundColorSpan = getRedForegroundColorSpan();
        stringBuilder.append("Smear ");
        switch (testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.TEST_RESULT)) {
            case "one_plus":
                stringBuilder.append("1+", redForegroundColorSpan);
                break;
            case "two_plus":
                stringBuilder.append("2+", redForegroundColorSpan);
                break;
            case "three_plus":
                stringBuilder.append("3+", redForegroundColorSpan);
                break;
            case "scanty":
                stringBuilder.append("Scanty", redForegroundColorSpan);
                break;
            case "negative":
                stringBuilder.append("Negative", getBlackForegroundColorSpan());
                break;
            default:
                stringBuilder.append(WordUtils.capitalize(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.TEST_RESULT).substring(0, 2)), redForegroundColorSpan);
                break;
        }
        stringBuilder.append("\n");
        return stringBuilder;
    }


    public TbrSpannableStringBuilder getCultureResultStringBuilder(Map<String, String> testResults, TbrSpannableStringBuilder stringBuilder) {
        ForegroundColorSpan colorSpan = testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.CULTURE_RESULT).equals(org.smartregister.bidan.constant.BidanConstants.RESULT.POSITIVE) ? getRedForegroundColorSpan() : getBlackForegroundColorSpan();
        stringBuilder.append("Culture ");
        stringBuilder.append(WordUtils.capitalizeFully(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.CULTURE_RESULT).substring(0, 3)), colorSpan);
        stringBuilder.append("\n");
        return stringBuilder;
    }


    public TbrSpannableStringBuilder getXRayResultStringBuilder(Map<String, String> testResults, TbrSpannableStringBuilder stringBuilder) {
        ForegroundColorSpan blackForegroundColorSpan = getBlackForegroundColorSpan();
        stringBuilder.append("Chest X-Ray ");
        if (testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.XRAY_RESULT).equals("indicative")) {
            stringBuilder.append("Indicative", getRedForegroundColorSpan());
        } else {
            stringBuilder.append("Not Indicative", blackForegroundColorSpan);
        }
        stringBuilder.append("\n");
        return stringBuilder;
    }

    private ForegroundColorSpan getRedForegroundColorSpan() {
        ForegroundColorSpan redForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.test_result_positive_red));
        return redForegroundColorSpan;
    }

    private ForegroundColorSpan getBlackForegroundColorSpan() {
        ForegroundColorSpan blackForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.test_result_negative_black));
        return blackForegroundColorSpan;
    }

    public TbrSpannableStringBuilder getXpertResultStringBuilder(Map<String, String> testResults, TbrSpannableStringBuilder stringBuilder, boolean withOtherResults) {
        stringBuilder.append("Gene Xpert ");
        ForegroundColorSpan blackForegroundColorSpan = getBlackForegroundColorSpan();
        stringBuilder.append(withOtherResults ? "Xpe " : "MTB ");
        stringBuilder.append(processXpertResult(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.MTB_RESULT)), getColorSpan(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.MTB_RESULT).equals(org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.DETECTED)));
        if (testResults.containsKey(org.smartregister.bidan.constant.BidanConstants.RESULT.ERROR_CODE)) {
            stringBuilder.append(" ");
            stringBuilder.append(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.ERROR_CODE), blackForegroundColorSpan);
        } else if (testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.MTB_RESULT).equals(org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.DETECTED) && testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.RIF_RESULT) != null) {
            stringBuilder.append(withOtherResults ? "/ " : " / RIF ");
            stringBuilder.append(processXpertResult(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.RIF_RESULT)), getColorSpan(testResults.get(org.smartregister.bidan.constant.BidanConstants.RESULT.RIF_RESULT).equals(org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.DETECTED)));
        }
        stringBuilder.append("\n");
        return stringBuilder;
    }

    public String processXpertResult(String result) {

        if (result == null)
            return "-ve";
        switch (result) {
            case org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.DETECTED:
                return "+ve";
            case org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.NOT_DETECTED:
                return "-ve";
            case org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.INDETERMINATE:
                return "?";
            case org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.ERROR:
                return "err";
            case org.smartregister.bidan.constant.BidanConstants.TEST_RESULT.XPERT.NO_RESULT:
                return "No result";
            default:
                return result;
        }
    }

    private ForegroundColorSpan getColorSpan(boolean isPositive) {
        return isPositive ? getRedForegroundColorSpan() : getBlackForegroundColorSpan();
    }
}
