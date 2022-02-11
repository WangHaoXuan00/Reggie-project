package com.itwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.dto.SetmealDto;
import com.itwang.entity.Setmeal;

import java.util.List;

/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 13:24
 */
public interface SetmealService extends IService<Setmeal> {

    /*新增套餐,同时保存套餐和菜品的关联关系*/
    public void saveWithDish(SetmealDto setmealDto);

    /*删除套餐，同时需要删除套餐和菜品的关联数据*/
    public void removeWithDish(List<Long> ids);

}
