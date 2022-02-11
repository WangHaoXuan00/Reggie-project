package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 13:24
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.service.DishFlavorService;
import com.itwang.dto.DishDto;
import com.itwang.entity.Dish;
import com.itwang.entity.DishFlavor;
import com.itwang.mapper.DishMapper;
import com.itwang.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Whx
 * @packageName: com.itwang.service.Impl
 * @ClassName: DishServiceImpl
 * @Description:
 * @data 2022/1/30 TIME:13:24
 */
@Service
@Slf4j
public class DishSderviceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /*新增菜品，同时保存对应的口味数据*/
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
//保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id
//菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
//        保存菜品口味到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /*根据id查询菜品信息和对应的口味信息*/
    @Override
    public DishDto getByIdWithFlavor(Long id) {
//        查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
//        查询当前菜品对应的口味信息，从dish——flavor查
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(lqw);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
//        更新dish表基本信息
        this.updateById(dishDto);
//清理当前菜品对应的口味数据
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(lqw);
//        添加当前提交过来的口味数据--dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }


}
