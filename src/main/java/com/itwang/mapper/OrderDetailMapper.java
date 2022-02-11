package com.itwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwang.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 20:39
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
