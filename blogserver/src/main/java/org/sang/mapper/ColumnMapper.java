package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;

import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Mapper
public interface ColumnMapper {
    List<Column> getAllCategories();

    /**
     * 根据id批量删除专栏
     * @param ids
     * @return
     */
    int deleteColumnByIds(@Param("ids") List<Long> ids);

    int updateColumn(Column column);

    int addColumn(Column column);

    // 专栏分页查询
    List<Column> pageQuery(ColumnPageDTO columnPageDTO);

    /**
     * 根据id查询专栏
     * @param id
     * @return
     */
    Column getColumnById(Long id);
}
