package by.tareltos.fcqdelivery.command;

import by.tareltos.fcqdelivery.command.impl.*;
import by.tareltos.fcqdelivery.receiver.UserReceiver;

public enum CommandType {
    MAIN(new MainCommand()),
    SINGIN(new SinginCommand()),
    LOGIN(new LoginCommand(new UserReceiver())),
    REGISTRATION(new RegistrationCommand(new UserReceiver())),
    RESETPASS(new ResetPasswordCommand(new UserReceiver())),
    LOGOUT(new LogoutCommand()),
    SAVEUSER(new SaveUserCommand(new UserReceiver()));


    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }


}
