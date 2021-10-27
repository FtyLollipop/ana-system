package tech.naive.anasystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Integer status;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static Result ok() {
        return new Result(null);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }
}