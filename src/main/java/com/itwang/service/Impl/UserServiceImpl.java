package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-02 21:00
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.entity.User;
import com.itwang.mapper.UserMapper;
import com.itwang.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.itwang.service.Impl
 * @ClassName: UserServiceImpl
 * @Description:
 * @author Whx
 * @data 2022/2/2 TIME:21:00
 * */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
