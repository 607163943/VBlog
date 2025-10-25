package org.sang.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("登录实体数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
