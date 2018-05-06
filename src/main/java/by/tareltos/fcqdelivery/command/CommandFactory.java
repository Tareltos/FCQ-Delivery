package by.tareltos.fcqdelivery.command;

/**
 * Created by Vitali Tarelko on 27.03.2018.
 * tareltos@gmail.com; skype: tareltos
 */
public class CommandFactory {
    private final static CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String command) {
        CommandType type = CommandType.valueOf(command.toUpperCase());
        return type.getCommand();

    }
}
