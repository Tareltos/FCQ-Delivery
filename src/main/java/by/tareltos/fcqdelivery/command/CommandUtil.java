package by.tareltos.fcqdelivery.command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class CommandUtil {

    public static boolean loadProperies(HttpServletRequest request, Properties properties, String fileNamePrm ){
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter(fileNamePrm);
        try {
            properties.load(context.getResourceAsStream(filename));
        } catch (IOException e) {
            request.setAttribute("exception", "Failed to load data to send password to email. Please, try again");
            return false;
        }
        return true;
    }

}
