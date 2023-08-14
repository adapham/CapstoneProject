package vn.com.edu.fpt.capstoneproject.exception;

import java.util.function.Supplier;

public class ActionNotAllowAttemptException extends CommonException {

    private ActionNotAllowAttemptException(String message, Object... argument) {
        super(message, argument);
    }

    public static ActionNotAllowAttemptException of(String message) {
        return new ActionNotAllowAttemptException(message);
    }

    public static ActionNotAllowAttemptException of(String errorMessage, Object... arguments) {
        return new ActionNotAllowAttemptException(errorMessage, arguments);
    }

    public static Supplier<ActionNotAllowAttemptException> ofSupplier(String message) {
        return () -> of(message);
    }
}
