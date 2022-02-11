package com.itwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwang.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 13:21
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
