package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.po.Column;

import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Mapper
public interface CategoryMapper {
    List<Column> getAllCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Column column);

    int addCategory(Column column);

    // 专栏分页查询
    List<Column> pageQuery(ColumnDTO columnDTO);
}
