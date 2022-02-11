package com.itwang.common;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 12:04
 */

/**
 * @author Whx
 * @packageName: com.itwang.common
 * @ClassName: BaseContext
 * @Description:
 * @data 2022/1/30 TIME:12:04
 */
/*基于ThreadLocal封装的工具类，用于保存获取当前用户的id*/
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
/*获取值*/
    public static Long getCurrentId() {
        return threadLocal.get();
    }


}
