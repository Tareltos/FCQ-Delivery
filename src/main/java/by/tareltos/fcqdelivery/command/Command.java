package by.tareltos.fcqdelivery.command;

import by.tareltos.fcqdelivery.receiver.ReceiverException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

public interface Command {

     String execute(HttpServletRequest request) throws ReceiverException,CommandException, IOException, ServletException;

}