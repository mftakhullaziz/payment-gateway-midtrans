package com.app.gatepay.shared.utils;

import com.app.gatepay.shared.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {

    public static void requireNonNull(Object object, String message, HttpStatus httpStatus) {
        if (object == null) {
            throw new BusinessException(message, httpStatus.value());
        }
    }

    public static void requirePresent(Boolean present, String message, HttpStatus httpStatus) {
        if (Boolean.FALSE.equals(present)) {
            throw new BusinessException(message, httpStatus.value());
        }
    }
}
