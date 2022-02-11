package com.itwang.common;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-29 9:49
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Whx
 * @packageName: com.itwang.common
 * @ClassName: GlobalExceptionHandler
 * @Description:
 * @data 2022/1/29 TIME:9:49
 */
/*全局异常处理*/
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /*异常处理*/
    @ExceptionHandler(CustomException.class)
    public R<String> execeptionHandler(SQLIntegrityConstraintViolationException ex){
      log.error(ex.getMessage());
    /*  if (ex.getMessage().contains("Duplicate entry")){
          String[] split = ex.getMessage().split(" ");
          String erro = split[2] + " 已存在";
          return R.error(erro);

      }*/


        return R.error(ex.getMessage());
    }

}
