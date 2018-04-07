package by.tareltos.fcqdelivery.command;

public enum PagePath {


    PATH_USERS_PAGE("/jsp/users.jsp"),
    PATH_SINGIN_PAGE("/jsp/singin.jsp"),
    PATH_INF_PAGE("/jsp/inf.jsp"),
    PATH_COURIERS_PAGE("/jsp/couriers.jsp"),
    PATH_USER_INFO_PAGE ("/jsp/userInfo.jsp"),
    PATH_MAIN_PAGE("/index.jsp");

    private String path;

    PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
