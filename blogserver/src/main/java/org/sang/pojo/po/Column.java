package org.sang.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by sang on 2017/12/19.
 */
@ApiModel("专栏")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("columns")
public class Column {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("专栏名")
    private String columnName;

    @ApiModelProperty("创建时间")
    private Timestamp date;
}
