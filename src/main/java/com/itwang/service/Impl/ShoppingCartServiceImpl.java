package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 19:50
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.entity.ShoppingCart;
import com.itwang.mapper.ShoppingCartMapper;
import com.itwang.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.itwang.service.Impl
 * @ClassName: ShoppingCartServiceImpl
 * @Description:
 * @author Whx
 * @data 2022/2/3 TIME:19:50
 * */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
