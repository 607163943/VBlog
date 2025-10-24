package org.sang.service;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.sang.mapper.ColumnMapper;
import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;
import org.sang.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Service
public class ColumnService {
    @Autowired
    ColumnMapper columnMapper;

    public List<Column> getAllCategories() {
        return columnMapper.getAllCategories();
    }

    /**
     * 根据id批量删除专栏
     * @param ids
     * @return
     */
    public boolean deleteColumnByIds(List<Long> ids) {
        int result = columnMapper.deleteColumnByIds(ids);
        return result == ids.size();
    }

    public int updateColumn(ColumnDTO columnDTO) {
        Column column = BeanUtil.copyProperties(columnDTO, Column.class);
        return columnMapper.updateColumn(column);
    }

    public int addColumn(ColumnDTO columnDTO) {
        Column column = BeanUtil.copyProperties(columnDTO, Column.class);

        column.setDate(new Timestamp(System.currentTimeMillis()));
        return columnMapper.addColumn(column);
    }

    // 专栏分页查询
    public PageResult<Column> pageQuery(ColumnPageDTO columnPageDTO) {
        int page= columnPageDTO.getPage();
        int pageSize= columnPageDTO.getPageSize();
        PageHelper.startPage(page,pageSize);

        Page<Column> columns = columnMapper.pageQuery(columnPageDTO);

        return PageResult.<Column>builder().total(columns.getTotal()).results(columns.getResult()).build();
    }

    /**
     * 根据id查询专栏
     * @param id
     * @return
     */
    public Column getColumnById(Long id) {
        return columnMapper.getColumnById(id);
    }
}
