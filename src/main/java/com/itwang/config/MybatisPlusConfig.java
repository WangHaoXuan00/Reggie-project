package com.itwang.config;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-29 16:43
 */

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Whx
 * @packageName: com.itwang.config
 * @ClassName: MybatisPlusConfig
 * @Description:
 * @data 2022/1/29 TIME:16:43
 */
/*配置MP的分页插件*/
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
