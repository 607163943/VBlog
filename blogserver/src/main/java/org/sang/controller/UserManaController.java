package org.sang.controller;

import org.sang.pojo.po.RespBean;
import org.sang.pojo.po.Role;
import org.sang.pojo.po.User;
import org.sang.pojo.dto.UserDTO;
import org.sang.result.Result;
import org.sang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sang on 2017/12/24.
 */
@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/roles")
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    @PutMapping(value = "/user/enabled")
    public Result updateUserEnabled(@RequestBody UserDTO userDTO) {
        if (userService.updateUserEnabled(userDTO.getEnabled(), userDTO.getId()) == 1) {
            return Result.success("更新成功！");
        } else {
            return Result.success("更新失败！");
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean("success", "删除成功!");
        } else {
            return new RespBean("error", "删除失败!");
        }
    }

    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public RespBean updateUserRoles(Long[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }
}
