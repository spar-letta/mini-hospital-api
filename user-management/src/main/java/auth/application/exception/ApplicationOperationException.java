package auth.application.exception;

import auth.application.exception.handler.Translator;

import java.util.List;

public class ApplicationOperationException extends IllegalArgumentException {

    private List<String> errors;

    public ApplicationOperationException() {
       
    }

    public ApplicationOperationException(String s, Object... args) {
        super(Translator.toLocale(s, args));
    }

    /**
     *
     * @param message Error message
     */
    public ApplicationOperationException(String message) {
        super(message);
    }

    /**
     *
     * @param messages Error messages
     */
    public ApplicationOperationException(List<String> messages) {
        this.errors = messages;
    }

    /**
     *
     * @param cause {@link Throwable}
     */
    public ApplicationOperationException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message Error message
     * @param cause {@link Throwable}
     */
    public ApplicationOperationException(String message, Throwable cause) {
        super(message, cause);
    }


    public List<String> getErrors() {
        return errors;
    }

}
