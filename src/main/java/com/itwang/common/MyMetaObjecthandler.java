package com.itwang.common;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 10:00
 */
/*自定义元数据对象处理器*/
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @packageName: com.itwang.common
 * @ClassName: MyMetaObjecthandler
 * @Description:
 * @author Whx
 * @data 2022/1/30 TIME:10:00
 * */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {



   /*插入操作自动填充*/
    @Override
    public void insertFill(MetaObject metaObject) {

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
/*更新操作自动填充*/
    @Override
    public void updateFill(MetaObject metaObject) {

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

    }
}
