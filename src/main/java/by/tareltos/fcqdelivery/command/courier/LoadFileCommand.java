package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;


/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class LoadFileCommand implements Command {
    /**
     * Method load file in the directory and returns the path to the jsp page
     *
     * @return return the path to the jsp page
     * @see by.tareltos.fcqdelivery.command.Command
     */
    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String courierId = request.getParameter(COURIER_ID);
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
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
                        Courier courier = COURIER_RECEIVER.getCourier(courierId);
                        request.setAttribute("courier", courier);
                        return PagePath.PATH_EDIT_COURIER_FORM.getPath();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        } catch (ServletException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        request.setAttribute(MESSAGE, "fileUploadError.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}


