package com.itwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.entity.Orders;

/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 20:36
 */
public interface OrderService extends IService<Orders> {

    /*用户下单*/

    public void submit(Orders orders);
}
