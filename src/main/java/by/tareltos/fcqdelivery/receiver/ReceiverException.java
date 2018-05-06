package by.tareltos.fcqdelivery.receiver;

/**
 * Class of exception which throws if a RepositoryException was caught
 *
 * @autor Tarelko Vitali
 * @see java.lang.Exception
 */
public class ReceiverException extends Exception {

    public ReceiverException(String message) {
        super(message);
    }
    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @see java.lang.Exception
     */
    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     *
     * @return a string representation of the object.
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "ReceiverException{}" + this.getMessage();
    }
}
