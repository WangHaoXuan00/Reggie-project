package com.itwang.controller;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-30 16:57
 */

import com.itwang.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Whx
 * @packageName: com.itwang.controller
 * @ClassName: CommonController
 * @Description:
 * @data 2022/1/30 TIME:16:57
 */
/*文件的上传和下载*/
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /*文件上传*/
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        System.out.println("============");
        log.info(file.toString());
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        if (!dir.exists()) {
//            目录不存在
            dir.mkdirs();
        }
        try {
//            将临时文件转存到指定位置
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    /*文件下载*/
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        //输入流，通过输入流读取文件内容
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
//      输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
