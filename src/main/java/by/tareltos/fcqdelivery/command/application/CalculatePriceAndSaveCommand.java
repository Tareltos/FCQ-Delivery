package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class CalculatePriceAndSaveCommand implements Command {

    public CalculatePriceAndSaveCommand(ApplicationReceiver applicationReceiver) {
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException, SQLException {
        return null;
    }
}
