package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 20:47
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.entity.OrderDetail;
import com.itwang.mapper.OrderDetailMapper;
import com.itwang.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.itwang.service.Impl
 * @ClassName: OrderDetailServiceImpl
 * @Description:
 * @author Whx
 * @data 2022/2/3 TIME:20:47
 * */
@Service
@Slf4j
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
