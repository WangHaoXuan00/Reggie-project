package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 13:26
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.common.CustomException;
import com.itwang.service.SetmealService;
import com.itwang.dto.SetmealDto;
import com.itwang.entity.Setmeal;
import com.itwang.entity.SetmealDish;
import com.itwang.mapper.SetmealMapper;
import com.itwang.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Whx
 * @packageName: com.itwang.service.Impl
 * @ClassName: SetmealServiceImpl
 * @Description:
 * @data 2022/1/30 TIME:13:26
 */
@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /*新增套餐，同时需要保存套餐，和菜品的关联关系*/
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

//        保存套餐的基本信息。操作setmeal，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

//        保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);

    }

    /*删除套餐，同时需要删除套餐和菜品的关联数据*/
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {

        //     查询套餐状态，确定是否可用删除

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();

        lqw.in(Setmeal::getId, ids);
        lqw.eq(Setmeal::getStatus, 1);
        int count = this.count(lqw);
        if (count > 0) {
            //        如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售出，不能删除");
        }

        this.removeByIds(ids);
//
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //        如果可以删除，先删除套餐表中的数据--setmeal
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
