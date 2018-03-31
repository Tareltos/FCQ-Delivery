package by.tareltos.fcqdelivery.command;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

public interface Command {

     String execute(HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException;

}
