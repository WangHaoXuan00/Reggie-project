package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-27 21:57
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwang.common.R;
import com.itwang.entity.Employee;
import com.itwang.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: EmployeeController
 * @Description:
 * @data 2022/1/27 TIME:21:57
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*员工登录*/
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
// 1.将页面提交的密码password进行md5加密
        String password = employee.getPassword();
//        利用工具类对密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//2.根据页面提交的用户名username查询数据库
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, username);
        Employee employeeServiceOne = employeeService.getOne(lqw);
//        判断是否有无空
        if (employeeServiceOne == null) {
            return R.error("登陆失败，请检查用户名与密码");
        }
//       比对密码
        if (!employeeServiceOne.getPassword().equals(password)) {
            return R.error("登陆失败，请检查用户名与密码");
        }
        if (employeeServiceOne.getStatus() == 0) {
            return R.error("账号已被锁定禁用");
        }
//        运行到这一地步说明已经登陆成功
        request.getSession().setAttribute("employee", employeeServiceOne.getId());
        return R.success(employeeServiceOne);

    }

    /*员工退出*/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
//        清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");

        return R.success("退出成功");
    }

    /*新增员工*/
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
//    获得当前登录用户id
        Long empid = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empid);
        employee.setUpdateUser(empid);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    //    员工信息分页查询方法
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
//      构造分页构造器
        Page pageinfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper();

        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        添加排序条件
        lqw.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageinfo, lqw);
        return R.success(pageinfo);
    }

    /*根据id修改员工信息*/
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /*根据id查询员工信息*/
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee!=null){
        return R.success(employee);
    }
    return R.error("没有查询到员工信息");
    }
}
