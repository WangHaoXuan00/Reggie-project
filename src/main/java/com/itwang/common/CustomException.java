package com.itwang.common;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 13:39
 */

/**
 * @packageName: com.itwang.common
 * @ClassName: CustomException
 * @Description:
 * @author Whx
 * @data 2022/1/30 TIME:13:39
 * */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
