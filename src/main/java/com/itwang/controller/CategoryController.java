package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 12:33
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwang.common.R;
import com.itwang.entity.Category;
import com.itwang.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: CategoryController
 * @Description:
 * @data 2022/1/30 TIME:12:33
 */
/*分类管理*/
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*新增分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    //    分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
//        分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
//        条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, lqw);

        return R.success(pageInfo);


    }

    /*根据ID删除分类*/
    @DeleteMapping

    public R<String> delete(Long id) {
        log.info("删除分类，id={}", id);
        categoryService.remove(id);
//        categoryService.removeById(id);
        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);

        categoryService.updateById(category);
        return R.success("修改分类信息成功");

    }

    /*根据条件查询分类数据*/
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();

        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);


        return R.success(list);
    }
}


