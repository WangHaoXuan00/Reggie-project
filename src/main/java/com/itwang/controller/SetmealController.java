package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-02 2:37
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwang.common.R;
import com.itwang.dto.SetmealDto;
import com.itwang.entity.Category;
import com.itwang.entity.Setmeal;
import com.itwang.service.CategoryService;
import com.itwang.service.SetmealDishService;
import com.itwang.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: SetmealController
 * @Description:
 * @data 2022/2/2 TIME:2:37
 */
/*套餐管理*/
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /*新增套餐*/
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /*套餐分页查询*/
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();


        lqw.like(name != null, Setmeal::getName, name);
        lqw.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, lqw);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
//            分类id
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
//          根据分类id 查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {

//                分类名称
                String name1 = category.getName();
                setmealDto.setCategoryName(name1);

            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /*删除套餐*/
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list( Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lambdaQueryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);
        return R.success(list);
    }
}
