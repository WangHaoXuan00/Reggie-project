package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 20:17
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwang.common.R;
import com.itwang.dto.DishDto;
import com.itwang.entity.Category;
import com.itwang.entity.Dish;
import com.itwang.entity.DishFlavor;
import com.itwang.service.CategoryService;
import com.itwang.service.DishFlavorService;
import com.itwang.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: DishController
 * @Description:
 * @data 2022/1/30 TIME:20:17
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    //    分累service
    @Autowired
    private CategoryService categoryService;

    /*新增菜品*/
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
//        构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

//        条件构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();

//        添加过滤条件
        lqw.like(name != null, Dish::getName, name);
        lqw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, lqw);
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
//
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
//            分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);

            }

            return dishDto;
        }).collect(Collectors.toList());
//        List<DishDto> list = null;

        dishDtoPage.setRecords(list);
        dishService.page(pageInfo, lqw);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /*修改菜品*/
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());


        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /*根据条件查询对应的菜品数据*/
  /*  @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getStatus,1);
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lqw);
        return R.success(list);

    }*/

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getStatus, 1);
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lqw);

        List<DishDto> dishDtosList = list.stream().map((item) -> {
//
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
//            分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);

            }
//当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
//SQL
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtosList);

    }
}
