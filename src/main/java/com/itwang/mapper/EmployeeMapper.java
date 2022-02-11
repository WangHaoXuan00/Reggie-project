package com.itwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwang.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Whx
 * @company XXX
 * @create 2022-01-27 21:53
 */
@Mapper
public interface EmployeeMapper  extends BaseMapper<Employee> {


}
