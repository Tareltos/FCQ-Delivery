package by.tareltos.fcqdelivery.command;

/**
 * The ENUM class whith stores paths to jsp page
 *
 * @autor Tarelko Vitali
 */
public enum PagePath {

    PATH_USERS_PAGE("/WEB-INF/jsp/users.jsp"),
    PATH_SINGIN_PAGE("/WEB-INF/jsp/singin.jsp"),
    PATH_INF_PAGE("/WEB-INF/jsp/inf.jsp"),
    PATH_COURIERS_PAGE("/WEB-INF/jsp/couriers.jsp"),
    PATH_USER_INFO_PAGE("/WEB-INF/jsp/userInfo.jsp"),
    PATH_MAIN_PAGE("/index.jsp"),
    PATH_NEW_COURIER_FORM("/WEB-INF/jsp/newCourierForm.jsp"),
    PATH_EDIT_COURIER_FORM("/WEB-INF/jsp/editCourierForm.jsp"),
    PATH_APPLICATIONS_PAGE("/WEB-INF/jsp/applications.jsp"),
    PATH_NEWAPP_FORM_PAGE("/WEB-INF/jsp/newApplicationForm.jsp"),
    PATH_APPLICATION_INFO_PAGE("/WEB-INF/jsp/applicationInfo.jsp"),
    PATH_SELECT_COURIER_PAGE("/WEB-INF/jsp/selectCourier.jsp");

    private String path;

    PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
