package by.tareltos.fcqdelivery.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class CurrentDateTag extends TagSupport {

    final static Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag(){
        JspWriter out = pageContext.getOut();//returns the instance of JspWriter
        try {
            out.print(Calendar.getInstance().getTime());//printing date and time using JspWriter
        } catch (Exception e) {
           LOGGER.log(Level.ERROR, "Exception in doStartTag: " + e.getMessage());
        }
        return SKIP_BODY;//will not evaluate the body content of the tag
    }
}
