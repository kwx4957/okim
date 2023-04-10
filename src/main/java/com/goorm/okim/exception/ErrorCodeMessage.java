package com.goorm.okim.exception;


/**
 * 공통 에러 코드 및 메시지
 * code : 2000 부터 사용(auth: 1000 - 인증서버에서 사용 중, user 2000, group 3000, task 4000, item 5000)
 * ex, GROUP_NOT_FOUND(3000, "존재하지 않는 그룹입니다.")
 */
public enum ErrorCodeMessage {

    USER_DUPLICATE_EMAIL(2000, "해당 이메일로 가입하실 수 없습니다. 다시 확인 바랍니다."),
    TASK_NOT_FOUND(4000, "존재하지 않는 테스크 입니다."),
    GROUP_NOT_FOUND(3000, "존재하지 않는 그룹 입니다."),
    NOT_FOUND_USER(2001, "해당 유저는 존재하지 않습니다."),
    AUTH_REQUIRED_VERIFICATION_CODE(2002, "유효시간이 초과 되었거나, 일치하는 인증코드가 없습니다."),

    NOT_ACCESSIBLE(1500, "잘못된 접근입니다.");

    private final int code;
    private final String message;

    ErrorCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}