package org.sang.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    // 状态码
    private Integer code;
    // 数据
    private Object data;
    // 消息
    private String msg;

    public static Result of(Integer code, Object data,String msg) {
        return new Result(code, data, msg);
    }

    public static Result success(Object data,String msg) {
        return Result.of(200,data,msg);
    }

    public static Result success(Object data) {
        return Result.of(200,data,"success");
    }

    public static Result success(String msg) {
        return Result.of(200,null,msg);
    }

    public static Result success() {
        return success("success");
    }

    public static Result error(Integer code,String msg) {
        return Result.of(code,null,msg);
    }

    public static Result error(String msg) {
        return Result.error(500,msg);
    }

    public static Result error() {
        return error("error");
    }
}
