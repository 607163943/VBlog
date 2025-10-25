package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sang.pojo.dto.LoginDTO;
import org.sang.pojo.po.RespBean;
import org.sang.pojo.po.User;
import org.sang.result.Result;
import org.sang.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户登录接口")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    @ApiOperation("用户登录")
    @RequestMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {

        return Result.success(userService.login(loginDTO),"success");
    }

    @PostMapping("/reg")
    public RespBean reg(User user) {
        int result = userService.reg(user);
        if (result == 0) {
            //成功
            return new RespBean("success", "注册成功!");
        } else if (result == 1) {
            return new RespBean("error", "用户名重复，注册失败!");
        } else {
            //失败
            return new RespBean("error", "注册失败!");
        }
    }
}
