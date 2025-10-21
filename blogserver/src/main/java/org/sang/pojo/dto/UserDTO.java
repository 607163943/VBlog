package org.sang.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    // 用户id
    private Long id;
    // 用户名
    private Boolean enabled;
}
