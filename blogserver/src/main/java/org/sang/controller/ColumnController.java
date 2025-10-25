package org.sang.controller;


import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sang.pojo.dto.ColumnDTO;
import org.sang.pojo.dto.ColumnPageDTO;
import org.sang.pojo.po.Column;
import org.sang.result.PageResult;
import org.sang.result.Result;
import org.sang.service.IColumnService;
import org.sang.service.impl.ColumnServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 超级管理员专属Controller
 */
@Api(tags = "专栏管理接口")
@RestController
@RequestMapping("/admin/column")
@RequiredArgsConstructor
public class ColumnController {
    private final IColumnService columnService;

    private final ColumnServiceImpl columnServiceImpl;

    /**
     * 分页查询专栏
     * @param columnPageDTO
     * @return
     */
    @ApiOperation("分页查询专栏")
    @GetMapping
    public Result<PageResult<Column>>pageQuery(ColumnPageDTO columnPageDTO) {
        PageResult<Column> pageColumns= columnService.pageQuery(columnPageDTO);
        return Result.success(pageColumns);
    }

    /**
     * 根据id获取专栏
     * @return
     */
    @ApiOperation("根据id获取专栏")
    @GetMapping("/{id}")
    public Result<Column> getColumnById(@PathVariable("id") Long id) {
        Column column=columnService.getById(id);
        return Result.success(column);
    }

    /**
     * 获取所有专栏
     * @return
     */
    @ApiOperation("获取所有专栏")
    @GetMapping("/all")
    public List<Column> getAllColumn() {
        return columnService.list();
    }

    /**
     * 根据id批量删除专栏
     * @param ids
     * @return
     */
    @ApiOperation("根据id批量删除专栏")
    @DeleteMapping
    public Result<Object> deleteColumnByIds(@RequestParam("ids") List<Long> ids) {
        boolean result=columnService.removeByIds(ids);
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
    public Result<Object> addColumn(@RequestBody ColumnDTO columnDTO) {

        if ("".equals(columnDTO.getColumnName()) || columnDTO.getColumnName() == null) {
            return Result.error( "专栏名不能为空!");
        }

        Column column = BeanUtil.copyProperties(columnDTO, Column.class);

        column.setDate(new Timestamp(System.currentTimeMillis()));

        boolean result=columnService.save(column);

        if (result) {
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
    public Result<Object> updateColumn(@RequestBody ColumnDTO columnDTO) {
        Column column = BeanUtil.copyProperties(columnDTO, Column.class);
        boolean result= columnService.updateById(column);
        if (result) {
            return Result.success( "修改成功!");
        }
        return Result.error("修改失败！");
    }
}
