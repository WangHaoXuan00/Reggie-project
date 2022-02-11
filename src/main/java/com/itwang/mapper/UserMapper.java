package com.itwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwang.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Whx
 * @company XXX
 * @create 2022-02-02 20:59
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
