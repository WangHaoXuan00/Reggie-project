package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 21:04
 */

import com.itwang.common.R;
import com.itwang.entity.Orders;
import com.itwang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.itwang.controller
 * @ClassName: OrderController
 * @Description:
 * @author Whx
 * @data 2022/2/3 TIME:21:04
 * */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*用户下单*/
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }
}
