package com.gt.manager.util;

import com.gtop.media.oss.UploadOss;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传图片
 */
public class UploadUtil {
    public static String uploadImg(MultipartFile file, String prefixDir, String fileName) throws IOException {
        boolean flag = UploadOss.putObject(file.getInputStream(), file.getSize(), prefixDir+"/", fileName, 1);
        if(flag){
            return "http://img.goola.cn/"+prefixDir+"/"+fileName;
        }else{
            return null;
        }
    }
}
