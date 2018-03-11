package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/12.
 */

public class Constant {
    public static final int HOME_PAGE = 0;
    public static final int NOTICE_PAGE = 1;
    public static final int GROUP_PAGE = 2;
    public static final int PERSONAL_PAGE = 3;

    public static final String SERVICE_URI_NUBIA = "http://192.168.43.109:8080/DormitoryManagement/app";
    public static final String SERVICE_URI_GEE = "http://192.168.199.117:8080/DormitoryManagement/app";

    public static final String STUDENT_LOGIN_URL = "/student/login";
    public static final String STUDENT_REGISTER_URL = "/student/register";
    public static final String STUDENT_LOOK_NOTICES_URL = "/student/lookNotices";
    public static final String STUDENT_LOOK_REPAIRS_URL = "/student/lookRepairs";
    public static final String STUDENT_LOOK_VIOLATIONS_URL = "/student/lookViolations";
    public static final String STUDENT_LOOK_INFO_URL = "/student/lookInfo";
    public static final String STUDENT_LOOK_BILLS_URL = "/student/lookBills";
    public static final String STUDENT_PAY_BILLS_URL = "/student/payBills/";
    public static final String STUDENT_DECLARE_REPAIR_URL = "/student/declareRepair";
    public static final String STUDENT_LOGOUT_URL = "/student/logout";

    public static final String DORADMIN_LOGIN_URL = "/dorAdmin/login";
    public static final String DORADMIN_REGISTER_URL = "/dorAdmin/register";
    public static final String DORADMIN_LOOK_STUDENT_URL = "/dorAdmin/lookStudent/";
    public static final String DORADMIN_LOOK_STUDENTS_URL = "/dorAdmin/lookStudents/";
    public static final String DORADMIN_LOOK_DORMITORY_URL = "/dorAdmin/lookDormitory/";
    public static final String DORADMIN_LOOK_NOTICES_URL = "/dorAdmin/lookNotices/";
    public static final String DORADMIN_ADD_NOTICE_URL = "/dorAdmin/addNotice";
    public static final String DORADMIN_ADD_VIOLATION_URL = "/dorAdmin/addViolation/";
    public static final String DORADMIN_LOOK_VIOLATIONS_URL = "/dorAdmin/lookViolation";
    public static final String DORADMIN_LOGOUT_URL = "/dorAdmin/logout";

    public static final String ADMIN_LOGIN_URL = "/admin/login";
    public static final String ADMIN_REGISTER_URL = "/admin/register";
}
