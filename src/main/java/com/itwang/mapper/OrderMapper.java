package com.itwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwang.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;

import javax.annotation.ManagedBean;

/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 20:38
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
