package org.sang.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("基础DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    @ApiModelProperty("id")
    private Integer id;
}
