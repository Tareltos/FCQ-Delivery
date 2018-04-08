package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

public class CreateCourierCommand implements Command {

    public CreateCourierCommand(CourierReceiver courierReceiver) {
    }

    @Override
    public String execute(HttpServletRequest request) throws  ReceiverException {
        return null;
    }
}
