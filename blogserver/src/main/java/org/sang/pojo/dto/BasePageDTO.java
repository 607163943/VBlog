package org.sang.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDTO {
    // 页数
    private Integer page;
    // 页码
    private Integer pageSize;
}
