package org.smartregister.bidan.constant;

import org.smartregister.AllConstants;
import org.smartregister.bidan.BuildConfig;

public class BidanConstants extends AllConstants {
    public static final long MAX_SERVER_TIME_DIFFERENCE = BuildConfig.MAX_SERVER_TIME_DIFFERENCE;
    public static final boolean TIME_CHECK = BuildConfig.TIME_CHECK;
    public static final String LAST_SYNC_TIMESTAMP = "LAST_SYNC_TIMESTAMP";
    public static final String LAST_CHECK_TIMESTAMP = "LAST_SYNC_CHECK_TIMESTAMP";
    public static final String LAST_VIEWS_SYNC_TIMESTAMP = "LAST_VIEWS_SYNC_TIMESTAMP";

    public static final String PATIENT_TABLE_NAME = "ec_patient";
    public static final String CONTACT_TABLE_NAME = "ec_contact";
    public static final String BIDAN_ID = "BIDAN ID";

    public static final String VIEW_CONFIGURATION_PREFIX = "ViewConfiguration_";

    public static final String PTB = "ptb";
    public static final String EPTB = "eptb";
    public static final String PULMONARY = "Pulmonary";
    public static final String EXTRA_PULMONARY = "Extra Pulmonary";

    public static class INTENT_KEY {
        public static final String FULL_NAME = "full_name";
        public static final String IS_REMOTE_LOGIN = "is_remote_login";
        public static final String REGISTER_TITLE = "register_title";
        public static final String PATIENT_DETAIL_MAP = "patient_detail_map";
        public static final String LAST_SYNC_TIME_STRING = "last_manual_sync_time_string";
        public static final String TB_REACH_ID = "tb_reach_id";
    }

    public static class CHAR {
        public static final String SPACE = " ";
        public static final String HASH = "#";
        public static final String NO_CHAR = "";
        public static final String COLON = ":";
        public static final String UNDERSCORE = "_";
    }

    public static class CONFIGURATION {
        public static final String LOGIN = "login";
        public static final String HOME = "home";
        public static final String MAIN = "main";
        public static final String LANG = "lang";
        public static final String PATIENT_DETAILS_PRESUMPTIVE = "patient_details_presumptive";
        public static final String PATIENT_DETAILS_POSITIVE = "patient_details_positive";
        public static final String PATIENT_DETAILS_INTREATMENT = "patient_details_intreatment";

        public static class COMPONENTS {
            public static final String PATIENT_DETAILS_DEMOGRAPHICS = "component_patient_details_demographics";
            public static final String PATIENT_DETAILS_RESULTS = "component_patient_details_results";
            public static final String PATIENT_DETAILS_SERVICE_HISTORY = "component_patient_details_service_history";
            public static final String PATIENT_DETAILS_CONTACT_SCREENING = "component_patient_details_contact_screening";
            public static final String PATIENT_DETAILS_FOLLOWUP = "component_patient_details_followup";
            public static final String PATIENT_DETAILS_BMI = "component_patient_details_bmi";
        }
    }

    public static final class GENDER {

        public static final String MALE = "male";
        public static final String FEMALE = "female";
        public static final String TRANSGENDER = "transgender";
    }

    public static final class KEY {
        public static final String _ID = "_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String FIRST_ENCOUNTER = "first_encounter";
        public static final String DATE = "date";
        public static final String TBREACH_ID = "tbreach_id";
        public static final String DOB = "dob";
        public static final String GENDER = "gender";
        public static final String PARTICIPANT_ID = "participant_id";
        public static final String NEXT_VISIT_DATE = "next_visit_date";
        public static final String PATIENT_TYPE = "patient_type";
        public static final String SITE_OF_DISEASE = "site_of_disease";
        public static final String TREATMENT_INITIATION_DATE = "treatment_initiation_date";
        public static final String CLIENT = "client";
        public static final String AGE = "age";
        public static final String PROGRAM_ID = "program_id";
        public static final String BASE_ENTITY_ID = "base_entity_id";
        public static final String DIAGNOSIS_DATE = "diagnosis_date";
        public static final String BASELINE = "baseline";
        public static final String SMR_NEXT_VISIT_DATE = "smear_due_date";
        public static final String PRESUMPTIVE = "presumptive";
        public static final String CONFIRMED_TB = "confirmed_tb";
        public static final String DATE_REMOVED = "date_removed";
        public static final String ATTRIBUTE_DATEREMOVED = "dateRemoved";//different
        public static final String PARENT_ENTITY_ID = "parent_entity_id";
        public static final String RELATIONAL_ID = "relational_id";
        public static final String INDEX = "_index";
        public static final String TREATMENT_REGIMEN = "treatment_regimen";
        public static final String OTHER_REGIMEN = "regimen_oth";
        public static final String TREATMENT_PHASE = "treatment_phase";
        public static final String TREATMENT_MONTH = "month";
        public static final String LAST_INTERACTED_WITH = "last_interacted_with";
        public static final String DEATHDATE = "deathdate";
    }

    public static final class FORM {
        public static final String NEW_PATIENT_REGISTRATION = "add_presumptive_patient";
        public static final String RESULT_SMEAR = "result_smear";
        public static final String RESULT_CHEST_XRAY = "result_chest_xray";
        public static final String RESULT_CULTURE = "result_culture";
        public static final String DIAGNOSIS = "diagnosis";
        public static final String RESULT_GENE_EXPERT = "result_gene_xpert";
        public static final String CONTACT_SCREENING = "contact_screening";
        public static final String REMOVE_PATIENT = "remove_patient";
        public static final String TREATMENT_OUTCOME = "treatment_outcome";
    }

    public enum ScreenStage {
        NOT_SCREENED, PRESUMPTIVE, SCREENED, POSITIVE, IN_TREATMENT
    }

    public static final class TEST_RESULT {
        public static final class XPERT {
            public static final String DETECTED = "detected";
            public static final String NOT_DETECTED = "not_detected";
            public static final String INDETERMINATE = "indeterminate";
            public static final String ERROR = "error";
            public static final String NO_RESULT = "no_result";
        }
    }

    public static final class RESULT {

        public static final String ERROR_CODE = "error_code";
        public static final String POSITIVE = "positive";

        public static final String MTB_RESULT = "mtb_result";
        public static final String RIF_RESULT = "rif_result";
        public static final String XRAY_RESULT = "xray_result";
        public static final String CULTURE_RESULT = "culture_result";
        public static final String TEST_RESULT = "test_result";
    }

    public static final class EVENT {

        public static final String REMOVE_PATIENT = "Remove Patient";
        public static final String TREATMENT_OUTCOME = "Treatment Outcome";
        public static final String SCREENING = "screening";
        public static final String POSITIVE_TB_PATIENT = "positive TB patient";
        public static final String CONTACT_SCREENING = "Contact Screening";
    }

    public static final class REGISTER_COLUMNS {
        public static final String PATIENT = "patient";
        public static final String RESULTS = "results";
        public static final String DIAGNOSE = "diagnose";
        public static final String XPERT_RESULTS = "xpert_results";
        public static final String SMEAR_RESULTS = "smr_results";
        public static final String TREAT = "treat";
        public static final String DIAGNOSIS = "diagnosis";
        public static final String INTREATMENT_RESULTS = "intreatment_results";
        public static final String FOLLOWUP_SCHEDULE = "followup_schedule";
        public static final String SMEAR_SCHEDULE = "smr_schedule";
        public static final String TREATMENT = "treatment";
        public static final String FOLLOWUP = "followup";
        public static final String BASELINE = "baseline";

    }

    public static final class VIEW_CONFIGS {

        public static final String COMMON_REGISTER_HEADER = "common_register_header";
        public static final String COMMON_REGISTER_ROW = "common_register_row";


        public static final String PRESUMPTIVE_REGISTER = "presumptive_register";
        public static final String PRESUMPTIVE_REGISTER_HEADER = "presumptive_register_header";
        public static final String PRESUMPTIVE_REGISTER_ROW = "presumptive_register_row";

        public static final String POSITIVE_REGISTER = "positive_register";
        public static final String POSITIVE_REGISTER_HEADER = "positive_register_header";
        public static final String POSITIVE_REGISTER_ROW = "positive_register_row";

        public static final String INTREATMENT_REGISTER = "intreatment_register";
        public static final String INTREATMENT_REGISTER_HEADER = "intreatment_register_header";
        public static final String INTREATMENT_REGISTER_ROW = "intreatment_register_row";

    }

    public static final class ENKETO_FORMS {
        public static final String SCREENING_FORM = "add_presumptive_patient";
        public static final String GENE_XPERT = "result_gene_xpert";
        public static final String SMEAR = "result_smear";
        public static final String CHEST_XRAY = "result_chest_xray";
        public static final String CULTURE = "result_culture";
        public static final String DIAGNOSIS = "diagnosis";
        public static final String ADD_POSITIVE_PATIENT = "add_positive_patient";
        public static final String TREATMENT_INITIATION = "treatment_initiation";
        public static final String FOLLOWUP_VISIT = "followup_visit";
        public static final String ADD_TB_CONTACT = "add_tb_contact";
        public static final String ADD_IN_TREATMENT_PATIENT = "add_intreatment_patient";
        public static final String CONTACT_SCREENING = "contact_screening";

    }
}
