package org.sang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;

/**
 * Created by sang on 2017/12/19.
 */
@Mapper
public interface ColumnMapper extends BaseMapper<Column> {
    // 专栏分页查询
    Page<Column> pageQuery(ColumnPageDTO columnPageDTO);
}
