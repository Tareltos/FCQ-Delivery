package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class SelectCourierCommand implements Command {
    public SelectCourierCommand(CourierReceiver courierReceiver) {
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException, SQLException {
        return null;
    }
}
