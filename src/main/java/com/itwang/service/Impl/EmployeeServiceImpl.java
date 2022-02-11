package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-27 21:55
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.service.EmployeeService;
import com.itwang.entity.Employee;
import com.itwang.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.itwang.service.Impl
 * @ClassName: EmployeeServiceImpl
 * @Description:
 * @author Whx
 * @data 2022/1/27 TIME:21:55
 * */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


}
