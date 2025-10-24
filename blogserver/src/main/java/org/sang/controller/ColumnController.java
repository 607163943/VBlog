package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;
import org.sang.result.Result;
import org.sang.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 超级管理员专属Controller
 */
@Api(tags = "专栏管理接口")
@RestController
@RequestMapping("/admin/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    /**
     * 分页查询专栏
     * @param columnPageDTO
     * @return
     */
    @ApiOperation("分页查询专栏")
    @GetMapping
    public Result pageQuery(ColumnPageDTO columnPageDTO) {
        List<Column> columns=columnService.pageQuery(columnPageDTO);
        return Result.success(columns);
    }

    /**
     * 根据id获取专栏
     * @return
     */
    @ApiOperation("根据id获取专栏")
    @GetMapping("/{id}")
    public Result getColumnById(@PathVariable("id") Long id) {
        Column column= columnService.getColumnById(id);
        return Result.success(column);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Column> getAllCategories() {
        return columnService.getAllCategories();
    }

    /**
     * 根据id批量删除专栏
     * @param ids
     * @return
     */
    @ApiOperation("根据id批量删除专栏")
    @DeleteMapping
    public Result deleteColumnByIds(@RequestParam("ids") List<Long> ids) {
        boolean result = columnService.deleteColumnByIds(ids);
        if (result) {
            return Result.success("删除成功!");
        }
        return Result.error("删除失败!");
    }

    /**
     * 添加专栏
     * @param columnDTO
     * @return
     */
    @ApiOperation("添加专栏")

    @PostMapping
    public Result addColumn(@RequestBody ColumnDTO columnDTO) {

        if ("".equals(columnDTO.getColumnName()) || columnDTO.getColumnName() == null) {
            return Result.error( "专栏名不能为空!");
        }

        int result = columnService.addColumn(columnDTO);

        if (result == 1) {
            return Result.success( "添加成功!");
        }
        return Result.error( "添加失败!");
    }

    /**
     * 根据id修改专栏
     * @param columnDTO
     * @return
     */
    @ApiOperation("根据id修改专栏")
    @PutMapping
    public Result updateColumn(@RequestBody ColumnDTO columnDTO) {
        int i = columnService.updateColumn(columnDTO);
        if (i == 1) {
            return Result.success( "修改成功!");
        }
        return Result.error("修改失败！");
    }
}
