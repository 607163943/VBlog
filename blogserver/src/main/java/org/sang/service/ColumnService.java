package org.sang.service;

import com.github.pagehelper.PageHelper;
import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.po.Column;
import org.sang.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Service
@Transactional
public class ColumnService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Column> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryMapper.deleteCategoryByIds(split);
        return result == split.length;
    }

    public int updateCategoryById(Column column) {
        return categoryMapper.updateCategoryById(column);
    }

    public int addCategory(Column column) {
        column.setDate(new Timestamp(System.currentTimeMillis()));
        return categoryMapper.addCategory(column);
    }

    // 专栏分页查询
    public List<Column> pageQuery(ColumnDTO columnDTO) {
        int page=columnDTO.getPage();
        int pageSize=columnDTO.getPageSize();
        PageHelper.startPage(page,pageSize);

        return categoryMapper.pageQuery(columnDTO);
    }
}
