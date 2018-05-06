package by.tareltos.fcqdelivery.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface is used to obtain path to jsp page
 *
 * @autor Tarelko Vitali
 */
public interface Command {

    /**
     * Method returns the path to the jsp page
     *
     * @param request servlet request parameter
     * @return return the path to the jsp page
     */
    String execute(HttpServletRequest request);

}