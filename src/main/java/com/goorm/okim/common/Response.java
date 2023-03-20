package com.goorm.okim.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class Response {
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    /**
     * 200
     *
     *    {
     *      "code": "0",
     *      "message": "성공"
     *      "data": {
     *          "id": 1234,
     *          "name": "John Smith",
     *          "email": "john.smith@example.com"
     *     }
     */
    public static ResponseEntity<Response> success(Object data) {
        Response body = new Response();
        body.code = 0;
        body.message = "success";
        body.data = data;
        return ResponseEntity.ok(body);
    }

    /**
     *  400
     *
     *  {
     *      "code": 2033, # error code
     *      "message": 해당 item 은 삭제할 수 없습니다.
     *  }
     */
    public static ResponseEntity<Response> failBadRequest(int code, String message) {
        Response body = new Response();
        body.code = code;
        body.message = message;
        return ResponseEntity.badRequest().body(body);
    }

    /**
     *  404
     *
     *  {
     *      "code": 2031, # error code
     *      "message": 존재하지 않는 TASK 입니다.
     *  }
     */
    public static ResponseEntity<Response> failNotFound(int code, String message) {
        Response body = new Response();
        body.code = code;
        body.message = message;
        return ResponseEntity.badRequest().body(body);
    }

}
