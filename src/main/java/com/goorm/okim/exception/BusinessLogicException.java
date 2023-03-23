package com.goorm.okim.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BusinessLogicException extends RuntimeException{
    private ErrorCodeMessage errorCodeMessage;
}
