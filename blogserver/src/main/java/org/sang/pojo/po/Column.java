package org.sang.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by sang on 2017/12/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    // id
    private Long id;
    // 专栏名
    private String columnName;
    // 创建时间
    private Timestamp date;
}
