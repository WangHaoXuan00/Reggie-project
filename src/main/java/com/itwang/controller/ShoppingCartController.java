package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 19:51
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.common.BaseContext;
import com.itwang.common.R;
import com.itwang.entity.ShoppingCart;
import com.itwang.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: ShoppingCartController
 * @Description:
 * @data 2022/2/3 TIME:19:51
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    //    添加购物车
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {

        log.info("购物车数据：{}", shoppingCart);

//        设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
//        查询一下，当前菜品或者套餐，是否在购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null) {
//            是菜品
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
//            是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());


        }

        ShoppingCart cartServiceOne = shoppingCartService.getOne(lqw);

        if (cartServiceOne != null) {
            //        如果有，在原来的数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(shoppingCart);
        } else {

//        如果不存在，就添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
//

        return R.success(cartServiceOne);
    }

  /*  //删除菜品数量
    @PostMapping("/sub")
    public R<ShoppingCart> delete(@RequestBody ShoppingCart shoppingCart){
        log.info("进入删除操作");
        log.info("购物车的数据：{}",shoppingCart);
        Long dishId = shoppingCart.getDishId();//当前菜品的id
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        if (dishId != null) {
//            是菜品
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
//            是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(lqw);

        if (cartServiceOne != null) {
            //        如果有，在原来的数量基础减一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number - 1);
            shoppingCartService.updateById(shoppingCart);
            return R.success(cartServiceOne);
        } *//*else {

//        如果不存在，就添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }*//*
        return R.error("删除失败");

    }*/

    //查看购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车。。。");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        lqw.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    /*清空购物车*/
    @DeleteMapping("clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }

}
