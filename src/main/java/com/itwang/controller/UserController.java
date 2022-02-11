package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-02 21:02
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.common.R;
import com.itwang.entity.User;
import com.itwang.service.UserService;
import com.itwang.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: UserController
 * @Description:
 * @data 2022/2/2 TIME:21:02
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /*发送验证码*/
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
// 获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //       生成随机的4位的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}", code);
            //        调用阿里云提供的短信服务
//            SMSUtils.sendMessage("瑞吉外卖", "", phone, code);
//         需要将生成的验证码保存到Session
            session.setAttribute(phone, code);
            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信发送失败");
    }

    /*移动端用户登录*/
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
log.info(map.toString());
// 获取手机号
        String phone = map.get("phone").toString();

//        获取验证码
        String code = map.get("code").toString();

//        从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
//        进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
if (codeInSession!=null&&codeInSession.equals(code)){
    //        如果能够比对成功，说明登陆成功
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();

    lqw.eq(User::getPhone,phone);
    User user = userService.getOne(lqw);
    if (user==null){

//        判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
        user = new User();
        user.setPhone(phone);
        user.setStatus(1);
        userService.save(user);

    }
    session.setAttribute("user",user.getId());
return R.success(user);
}
      return R.error("登陆失败");
    }
}
