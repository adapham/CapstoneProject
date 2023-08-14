package vn.com.edu.fpt.capstoneproject.exception;

import lombok.Getter;

import java.util.function.Supplier;

public class CommonException extends RuntimeException {

    @Getter
    private final Object[] arguments;

    protected CommonException(String errorMessage, Object... arguments) {
        super(errorMessage);
        this.arguments = arguments;
    }

    public static Supplier<CommonException> ofSupplier(String errorMessage, Object... arguments) {
        return () -> new CommonException(errorMessage, arguments);
    }

    public static CommonException of(String errorMessage, Object... arguments) {
        return ofSupplier(errorMessage, arguments).get();
    }

}