package gov.nsf.psm.solicitation.common;

public final class Constants {

    // Stored Procedure Fields
    public static final String RESULT_SET = "RESULT_SET";

    // Stored Procedure Mapper constants
    public static final String FUND_OP_ID        = "fundingOpId";
    public static final String FUND_OP_TITLE     = "fundingOpTitle";
    public static final String FUND_OP_TYPE      = "fundingOpType";
    public static final String FUND_OP_YEAR      = "fundingOpYear";
    public static final String FUND_OP_NUM       = "fundingOpNum";
    public static final String DIR_ID            = "directorateCode";
    public static final String DIR_NAME          = "directorateName";
    public static final String DIR_ABBRV         = "directorateAbbrv";
    public static final String DIR_LONG_NAME     = "directorateLongName";
    public static final String DIV_ID            = "divisionCode";
    public static final String DIV_NAME          = "divisionName";
    public static final String DIV_ABBRV         = "divisionAbbrv";
    public static final String DIV_LONG_NAME     = "divisionLongName";
    public static final String PGM_ELE_ID        = "pgmEleCode";
    public static final String PGM_ELE_NAME      = "pgmEleName";
    public static final String PGM_ELE_LONG_NAME = "pgmEleLongName";
    public static final String FUND_OP_EFFECTIVE_DATE    = "effectiveDate";
    public static final String FUND_OP_EXPIRE_DATE    = "expireDate";

    // Stored procedure input params
    public static final String IN_FUND_OP_ID = "fundingOpId";
    public static final String IN_DIR_ID     = "dirCode";
    public static final String IN_DIV_ID     = "divCode";
    public static final String IN_DIR_IDS_WHERE_CLAUSE     = "dirCodes";
    public static final String IN_DIV_IDS_WHERE_CLAUSE     = "divCodes";
    public static final String IN_PGM_ELE_IDS_WHERE_CLAUSE     = "pgmEleCodes";

    // Stored procedure input params - GPG
    public static final String IN_GPG_ELE_CD_LIST  = "progElemCdExclList";
    public static final String IN_GPG_WHERE_CLAUSE = "whereClause";

    
    
    // Error constants
    public static final String GET_ALL_PROGRAM_ELEMENTS_ERROR         = "Solicitation Data Service encountered an error while trying to get all program elements";
    public static final String GET_ALL_FUNDING_OPPORTUNITIES_ERROR    = "Solicitation Data Service encountered an error while trying to get all funding opportunitites";
    public static final String GET_ALL_DIVISIONS_ERROR                = "Solicitation Data Service encountered an error while trying to get all divisions";
    public static final String GET_ALL_DIRECTORATES_ERROR             = "Solicitation Data Service encountered an error while trying to get all directorates";
    public static final String GET_DIVISIONS_BY_FUND_OP_ID_ERROR      = "Solicitation Data Service encountered an error while trying to get divisions for funding opportunity";
    public static final String GET_PROGRAM_ELEMENTS_BY_DIV_CODE_ERROR = "Solicitation Data Service encountered an error while trying to get program elements for division";
    public static final String GET_DIRECTORATES_BY_FUND_OP_ID_ERROR   = "Solicitation Data Service encountered an error while trying to get directorate for funding opportunity";
    public static final String GET_DIRECTORATE_BY_FUND_OP_ID_ERROR    = "Solicitation Data Service encountered an error while trying to get directorate for funding opportunity";
    public static final String GET_FUND_OP_AND_UOCS_ERROR             = "Solicitation Data Service encountered an error while trying to get funding opportunity and uoc data for proposal";

    public static final String PROGRAM_ELEMENTS_NO_RESULTS_ERROR      = "No program element results were returned.";
    public static final String FUNDING_OPPORTUNITIES_NO_RESULTS_ERROR = "No funding opportunity results were returned.";
    public static final String DIVISIONS_NO_RESULTS_ERROR             = "No division results were returned.";
    public static final String DIRECTORATES_NO_RESULTS_ERROR          = "No directorate results were returned.";
    public static final String STATES_NO_RESULTS_ERROR          	  = "No states results were returned.";
    public static final String COUNTRY_NO_RESULTS_ERROR          	  = "No countries results were returned.";
    public static final String DUE_DATE_NO_RESULTS_ERROR          	  = "No due dates results were returned.";
    public static final String POSTAL_CODE_NO_RESULTS_ERROR           = "No postal code results were returned.";
    public static final String PI_NO_RESULTS_ERROR          	 	  = "No PI  results were returned.";

    public static final String STATE_CODE = "st_code";
    public static final String STATE_DESC = "st_name";
    public static final String COUNTRY_CODE ="ctry_code";
    public static final String COUNTRY_NAME = "ctry_name";
    
    public static final String DEADLINE_TYPE_DESC = "deadlineTypeDesc";
    public static final String DEADLINE_TYPE_CODE = "deadlineTypeCode";
    public static final String DEADLINE_DATE = "deadlineDate";
    public static final String FUNDING_OP_ID = "fundingOpId";
        
    public static final String PI_NSF_ID = "pi_ssn";
    public static final String PI_FIRST_NAME = "pi_frst_name";
    public static final String PI_LAST_NAME = "pi_last_name";
    public static final String PI_MIDDLE_INITIAL = "pi_mid_init";
    public static final String PI_DEGREE_YEAR = "pi_degr_yr";
    public static final String PI_ACADEMIC_DEGREE = "pi_acad_degr_txt";
    public static final String PI_PHONE_NUMBER = "pi_phon_num";
    public static final String PI_EMAIL = "pi_emai_addr";
    public static final String PI_DEPARTMENT_NAME = "pi_dept_name";
    public static final String PI_FAX_NUMBER = "pi_fax_num";
    public static final String PI_STREET_ADDR = "pi_str_addr";
    public static final String PI_STREET_ADDR_1 = "pi_str_addr_addl";
    public static final String PI_CITY_NAME = "cty_name";
    public static final String PI_STATE_CODE = "st_code";
    public static final String PI_ZIP_CODE= "zip_code";
    public static final String PI_COUNTRY_CODE = "ctry_code";
    
    public static final String DUE_DATE_ACCEPTED_ANYTIME_CODE ="3";

    
    private Constants() {
        super();
    }
}
