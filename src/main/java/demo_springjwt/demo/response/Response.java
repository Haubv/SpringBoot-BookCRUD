package demo_springjwt.demo.response;

import lombok.Data;

@Data
public class Response {
    public static final int CODE_SUCCESS = 0;

    public static final int CODE_PERMISSION = 1;

    public static final int CODE_NOT_FOUND = 2;

    public static final int CODE_ALREADY_EXIST = 3;

    public static final int CODE_BUSINESS = 4;

    protected Integer code;

    protected String message;

    public Response ok() {
        this.code = CODE_SUCCESS;
        return this;
    }

    public Response error() {
        this.code = CODE_BUSINESS;
        return this;
    }

    public Response code(int code) {
        this.code = code;
        return this;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    private Object data;

    public static Response build() {
        return new Response();
    }

    public Response data(Object data) {
        this.data = data;
        return this;
    }
}

