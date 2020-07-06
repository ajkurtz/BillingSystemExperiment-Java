package com.andykurtz.api.exception;

import org.springframework.http.HttpStatus;

public class ExceptionFactory {

    public static void throwExceptionForStatus(HttpStatus httpStatus, String message) {
        throwExceptionForStatus(httpStatus.value(), message);
    }

    public static void throwExceptionForStatus(int httpStatus, String message) {
        if (httpStatus == HttpStatus.NOT_FOUND.value()) {
            throw new NotFoundException(message);
        } else if ((httpStatus >= 300) && httpStatus < 500) {
            throw new BadRequestException(message);
        } else if (httpStatus >= 500) {
            throw new ServerErrorException(message);
        }
    }

}
