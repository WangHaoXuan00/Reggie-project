package com.itwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.entity.Category;


/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 12:29
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
