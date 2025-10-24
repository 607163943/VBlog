package org.sang.controller;

import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.po.Column;
import org.sang.pojo.po.RespBean;
import org.sang.result.Result;
import org.sang.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/column")
public class ColumnController {
    @Autowired
    ColumnService columnService;

    /**
     * 分页查询专栏
     * @param columnDTO
     * @return
     */
    @GetMapping
    public Result pageQuery(ColumnDTO columnDTO) {
        List<Column> columns=columnService.pageQuery(columnDTO);
        return Result.success(columns);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Column> getAllCategories() {
        return columnService.getAllCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = columnService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewCate(Column column) {

        if ("".equals(column.getCateName()) || column.getCateName() == null) {
            return new RespBean("error", "请输入栏目名称!");
        }

        int result = columnService.addCategory(column);

        if (result == 1) {
            return new RespBean("success", "添加成功!");
        }
        return new RespBean("error", "添加失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Column column) {
        int i = columnService.updateCategoryById(column);
        if (i == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
