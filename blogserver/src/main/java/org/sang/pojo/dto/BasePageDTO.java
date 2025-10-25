package org.sang.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("接触分页DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDTO {
    @ApiModelProperty(value = "页数")
    private Integer page;

    @ApiModelProperty(value = "页码")
    private Integer pageSize;
}
