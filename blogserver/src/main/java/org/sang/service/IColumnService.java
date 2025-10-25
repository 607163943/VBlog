package org.sang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;
import org.sang.result.PageResult;

public interface IColumnService extends IService<Column> {
    /**
     * 分页查询专栏
     * @param columnPageDTO
     * @return
     */
    PageResult<Column> pageQuery(ColumnPageDTO columnPageDTO);
}
