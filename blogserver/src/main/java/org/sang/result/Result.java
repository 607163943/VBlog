package org.sang.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("响应数据实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    // 状态码
    @ApiModelProperty("状态码")
    private Integer code;
    // 数据
    @ApiModelProperty("数据")
    private T data;
    // 消息
    @ApiModelProperty("消息")
    private String msg;

    public static <T> Result<T> of(Integer code, T data, String msg) {
        return new Result<>(code, data, msg);
    }

    public static <T> Result<T> success(T data, String msg) {
        return Result.of(200, data, msg);
    }

    public static <T> Result<T> success(T data) {
        return Result.of(200, data, "success");
    }

    public static <T> Result<T> success(String msg) {
        return Result.of(200, null, msg);
    }

    public static <T> Result<T> success() {
        return success("success");
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return Result.of(code, null, msg);
    }

    public static <T> Result<T> error(String msg) {
        return Result.error(500, msg);
    }

    public static <T> Result<T> error() {
        return error("error");
    }
}
