package com.itwang.config;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-27 12:49
 */

import com.itwang.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author Whx
 * @packageName: com.itwang.config
 * @ClassName: WebMvcConfig
 * @Description:
 * @data 2022/1/27 TIME:12:49
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*设置静态资源映射*/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");

    }

    /*扩展mvc消息转换器*/
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//创建消息转换器对象
        log.info("扩展消息转换器》》》》");
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器，底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

//        将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
//        super.extendMessageConverters(converters);
   log.info("消息转换器加载完毕");
    }
}
