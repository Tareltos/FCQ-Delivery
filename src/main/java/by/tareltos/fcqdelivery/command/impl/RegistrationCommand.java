package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.receiver.UserReceiver;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {



    public RegistrationCommand(UserReceiver userReceiver) {
    }

    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
