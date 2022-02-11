package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 12:29
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.common.CustomException;
import com.itwang.entity.Category;
import com.itwang.entity.Dish;
import com.itwang.entity.Setmeal;
import com.itwang.mapper.CategoryMapper;
import com.itwang.service.CategoryService;
import com.itwang.service.DishService;
import com.itwang.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Whx
 * @packageName: com.itwang.service.Impl
 * @ClassName: CategoryServiceImpl
 * @Description:
 * @data 2022/1/30 TIME:12:29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /*根据id删除分类，删除之前需要进行判断*/
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //        查询当前分类是否关联了菜品，如果关联则抛出业务异常
        lqw.eq(Dish::getCategoryId, id);
        int count = dishService.count(lqw);
        if (count > 0) {
//            已经关联了
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
//        查询当前分类是否关联了套餐，如果关联则抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
//            已经关联了
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
//    正常删除
        super.removeById(id);

    }
}
