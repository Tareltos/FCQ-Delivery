package by.tareltos.fcqdelivery.filter;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.tareltos.fcqdelivery.command.ParameterStore.LOGINED_USER;
import static by.tareltos.fcqdelivery.command.ParameterStore.MESSAGE;

@WebFilter(filterName = "blockedUserFilter", urlPatterns = {"/*"})
public class BlockedUserFilter implements Filter {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private final static Logger LOGGER = LogManager.getLogger();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession(true);
        User currentUser = (User) session.getAttribute("loginedUser");
        if (null != currentUser) {
            try {
                if (!UserReceiver.getInstance().checkUserStatus(currentUser.getEmail())) {
                    LOGGER.log(Level.WARN, "User is blocked");
                    session.setAttribute(LOGINED_USER, null);
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.ERROR, "Exception in userReceiver: " + e.getMessage());
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) {

    }

}
