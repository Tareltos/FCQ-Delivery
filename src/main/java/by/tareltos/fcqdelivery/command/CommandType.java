package by.tareltos.fcqdelivery.command;

import by.tareltos.fcqdelivery.command.application.*;
import by.tareltos.fcqdelivery.command.courier.*;
import by.tareltos.fcqdelivery.command.user.*;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.UserReceiver;

/**
 * The ENUM class whith stores command name
 *
 * @autor Tarelko Vitali
 */
public enum CommandType {
    MAIN(new MainCommand()),
    SING_IN(new SinginCommand()),
    LOG_IN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    RESET_PASS(new ResetPasswordCommand()),
    LOG_OUT(new LogoutCommand()),
    SAVE_USER(new SaveUserCommand()),
    GET_USERS(new AllUsersCommand()),
    CREATE_USER(new CreateUserCommand()),
    CHANGE_STATUS(new ChangeUserStatusCommand()),
    GET_COURIERS(new GetCouriersCommand()),
    GET_COURIERS_PG(new PaginationGetCouriersCommand()),
    CREATE_COURIER(new CreateCourierCommand()),
    COURIER_FORM(new CreateCourierFormCommand()),
    EDIT_COURIER(new EditCourierFormCommand()),
    UPDATE_COURIER(new UpdateCourierCommand()),
    GET_APPLICATIONS(new GetApplicationsCommand()),
    GET_NEWAPP_FORM(new GetNewApplicationFormCommand()),
    CREATE_APPLICATION(new CreateApplicationCommand()),
    GET_APP_DETAILS(new GetAppDetailsCommand()),
    SELECT_COURIER(new SelectCourierCommand()),
    CALCULATE_PRICE_AND_SAVE(new CalculatePriceAndSaveCommand()),
    DO_PAYMENT(new DoPaymentApplicationCommand()),
    COMPLETE_APPLICATION(new CompleteApplicationCommand()),
    DELETE_APPLICATION(new DeleteApplicationCommand()),
    CANCEL_APPLICATION(new CancelApplicationCommand()),
    LOAD_FILE(new LoadFileCommand()),
    FIND_APPLICATIONS(new FindApplicationsCommand());
    /**
     * Command field which defined while initialization
     */
    private Command command;

    /**
     * Constructs CommandType and initialize private command variable
     */
    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }


}
