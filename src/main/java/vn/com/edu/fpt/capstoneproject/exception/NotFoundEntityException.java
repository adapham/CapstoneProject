package vn.com.edu.fpt.capstoneproject.exception;

import lombok.Getter;

import java.util.function.Supplier;

public class NotFoundEntityException extends CommonException {

    private static final long serialVersionUID = -718639735490655218L;

    @Getter
    private String errorField;
    @Getter
    private final Object[] arguments;

    private NotFoundEntityException(String errorField, String message, Object... arguments) {
        super(message);
        this.errorField = errorField;
        this.arguments = arguments;
    }

    public static NotFoundEntityException of(String message) {
        return of(null, message);
    }

    public static NotFoundEntityException of(String errorField, String message, Object... arguments) {
        return ofSupplier(errorField, message, arguments).get();
    }

    public static Supplier<NotFoundEntityException> ofSupplier(String errorField, String errorMessage, Object... arguments) {
        return () -> new NotFoundEntityException(errorField, errorMessage, arguments);
    }

    public static NotFoundEntityException of(String errorField, String message) {
        return new NotFoundEntityException(errorField, message);
    }

    public static Supplier<NotFoundEntityException> ofSupplier(String message) {
        return ofSupplier(null, message);
    }

    public static Supplier<NotFoundEntityException> ofSupplier(String errorField, String message) {
        return () -> of(errorField, message);
    }

    public static void throwNow(String errorField, String message) throws NotFoundEntityException {
        throw of(errorField, message);
    }
}
