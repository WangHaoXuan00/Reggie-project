package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 21:03
 */

import com.itwang.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.itwang.controller
 * @ClassName: OrderDetailController
 * @Description:
 * @author Whx
 * @data 2022/2/3 TIME:21:03
 * */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
}
