package org.smartregister.bidan.database.helper;

/**
 * Created by ndegwamartin on 28/01/2018.
 */

public class DBQueryHelper {

    public static final String getPresumptivePatientRegisterCondition() {
        return " " + org.smartregister.bidan.constant.BidanConstants.KEY.PRESUMPTIVE + " =\"yes\" AND " + org.smartregister.bidan.constant.BidanConstants.KEY.CONFIRMED_TB + " IS NULL AND " + org.smartregister.bidan.constant.BidanConstants.KEY.DATE_REMOVED + " =\"\" ";
    }

    public static final String getPositivePatientRegisterCondition() {
        return " " + org.smartregister.bidan.constant.BidanConstants.KEY.CONFIRMED_TB + " = \"yes\" AND " + org.smartregister.bidan.constant.BidanConstants.KEY.TREATMENT_INITIATION_DATE + " IS NULL AND " + org.smartregister.bidan.constant.BidanConstants.KEY.DATE_REMOVED + " =\"\" ";
    }

    public static final String getIntreatmentPatientRegisterCondition() {
        return org.smartregister.bidan.constant.BidanConstants.KEY.TREATMENT_INITIATION_DATE + " IS NOT NULL AND " + org.smartregister.bidan.constant.BidanConstants.KEY.DATE_REMOVED + " =\"\" ";
    }
}
