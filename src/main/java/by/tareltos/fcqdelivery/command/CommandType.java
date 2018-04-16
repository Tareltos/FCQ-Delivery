package by.tareltos.fcqdelivery.command;

import by.tareltos.fcqdelivery.command.application.*;
import by.tareltos.fcqdelivery.command.courier.*;
import by.tareltos.fcqdelivery.command.user.*;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.UserReceiver;

public enum CommandType {
    MAIN(new MainCommand()),
    SING_IN(new SinginCommand()),
    LOG_IN(new LoginCommand(new UserReceiver())),
    REGISTRATION(new RegistrationCommand(new UserReceiver())),
    RESET_PASS(new ResetPasswordCommand(new UserReceiver())),
    LOG_OUT(new LogoutCommand()),
    SAVE_USER(new SaveUserCommand(new UserReceiver())),
    GET_USERS(new AllUsersCommand(new UserReceiver())),
    CREATE_USER(new CreateUserCommand(new UserReceiver())),
    CHANGE_STATUS(new ChangeUserStatusCommand(new UserReceiver())),
    GET_COURIERS(new GetCouriersCommand(new CourierReceiver())),
    CREATE_COURIER(new CreateCourierCommand(new CourierReceiver())),
    COURIER_FORM(new CreateCourierFormCommand()),
    EDIT_COURIER(new EditCourierFormCommand(new CourierReceiver())),
    UPDATE_COURIER(new UpdateCourierCommand(new CourierReceiver())),
    GET_APPLICATIONS(new GetApplicationsCommand(new ApplicationReceiver())),
    GET_NEWAPP_FORM(new GetNewApplicatinFormCommand()),
    CREATE_APPLICATION(new CreateApplicationCommand(new ApplicationReceiver())),
    GET_APP_DETAILS(new GetAppDetailsCommand(new ApplicationReceiver())),
    SELECT_COURIER(new SelectCourierCommand(new ApplicationReceiver())),
    CALCULATE_PRICE_AND_SAVE(new CalculatePriceAndSaveCommand(new ApplicationReceiver()));


    private Command command;


    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }


}
