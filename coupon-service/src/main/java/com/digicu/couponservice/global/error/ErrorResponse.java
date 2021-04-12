package com.digicu.couponservice.global.error;

import com.digicu.couponservice.global.error.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;

    public ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public static ErrorResponse of(final ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }
}
