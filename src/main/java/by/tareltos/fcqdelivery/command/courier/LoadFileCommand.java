package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class LoadFileCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String UPLOAD_DIR_PRM = "files";
    private static final String COURIER_ID_PRM = "id";
    private CourierReceiver courierReceiver;

    public LoadFileCommand(CourierReceiver courierReceiver) {
        this.courierReceiver = courierReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "Пользователь is null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (!"manager".equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "Нарушение прав доступа Пользователь: " + loginedUser.getRole().getRole());
            request.setAttribute("errorMessage", "У Вас нет прав доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String courierId = request.getParameter(COURIER_ID_PRM);
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR_PRM;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        LOGGER.log(Level.INFO, "Upload File Directory = " + fileSaveDir.getAbsolutePath());
        try {
            for (Part part : request.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                    request.setAttribute("uploadInfo", part.getSubmittedFileName());
                    if (courierId == null) {
                        return PagePath.PATH_NEW_COURIER_FORM.getPath();
                    } else {
                        Courier courier = courierReceiver.getCourier(courierId);
                        request.setAttribute("courier", courier);
                        return PagePath.PATH_EDIT_COURIER_FORM.getPath();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (ReceiverException e) {
            e.printStackTrace();
        }
        request.setAttribute("errorMessage", "Невозможно сохранить изображение на сервер");
        return PagePath.PATH_NEW_COURIER_FORM.getPath();
    }
}


