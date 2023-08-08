package vn.com.edu.fpt.capstoneproject.model.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String description;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.code();
        this.description = errorCode.description();
    }

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
