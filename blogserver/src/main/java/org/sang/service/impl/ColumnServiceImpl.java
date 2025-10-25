package org.sang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.sang.mapper.ColumnMapper;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;
import org.sang.result.PageResult;
import org.sang.service.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sang on 2017/12/19.
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {
    @Autowired
    ColumnMapper columnMapper;

    // 专栏分页查询
    public PageResult<Column> pageQuery(ColumnPageDTO columnPageDTO) {
        int page= columnPageDTO.getPage();
        int pageSize= columnPageDTO.getPageSize();
        PageHelper.startPage(page,pageSize);

        Page<Column> columns = this.baseMapper.pageQuery(columnPageDTO);

        return PageResult.<Column>builder().total(columns.getTotal()).results(columns.getResult()).build();
    }
}
