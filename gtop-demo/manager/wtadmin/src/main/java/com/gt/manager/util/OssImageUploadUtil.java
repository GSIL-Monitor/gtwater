package com.gt.manager.util;

import com.gt.gtop.entity.base.DataMessage;
import com.gtop.media.oss.UploadOss;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * oss上传图片
 *
 */
public class OssImageUploadUtil {
	
	/**
	 * oss水管家图片存放路径
	 */
	public static final String OSS_GTWATER_PATH = "gtwater/";
	
	/**
	 * oss水管家图片访问url
	 */
	public static final String OSS_GTWATER_URL = "http://img.goola.cn/";
	
	
	/**
	 * 
	 * @param base64Data base64为字符
	 * @param path 新目录
	 * @return
	 * @throws Exception
	 */
	public static DataMessage uploadBase64Image(String base64Data, String path) throws Exception{
		String dataPrix = "";// 图片格式
        String data = "";// 图片内容
        if (StringUtils.isBlank(base64Data)) {
			return new DataMessage(DataMessage.RESULT_FAILED, null, "上传失败，上传图片数据为空");
        } else {
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                dataPrix = d[0];
                data = d[1];
            } else {
				return new DataMessage(DataMessage.RESULT_FAILED, null, "上传失败，数据不合法");
            }
        }

        String suffix = "";
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrix) 
                || "data:image/jpg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        }else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        } else {
			return new DataMessage(DataMessage.RESULT_FAILED, null, "上传图片格式不合法");
        }

        //data去下空格
        data = data.replaceAll(" ", "");
        data = data.replaceAll("\n", "");
        data = data.replaceAll("\t", "");
        
        // 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        //System.out.println(data);
        byte[] bs = Base64Utils.decodeFromString(data);
        //限制文件大小为4m以内
        if(bs.length > 8*1024*1024*4){
			return new DataMessage(DataMessage.RESULT_FAILED, null, "图片文件大小必须在4M以内！");
        }
        
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");// 以日期分组
		String today = format.format(new Date());
        String realPath = path + today +"/";
        
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 新文件名格式：项目根路径+upload+日期+uuid+后缀名
        String newFileName = new StringBuffer(uuid).append(suffix).toString();
        System.out.println("文件保存至：" + newFileName);
        try {
        	//使用oss上传
        	boolean putObject = UploadOss.putObject(new ByteArrayInputStream(bs), bs.length, realPath, newFileName, 1);
        	if (!putObject) {
				return new DataMessage(DataMessage.RESULT_FAILED, null, "上传图片失败，请重试");
			}
        } catch (Exception ee) {
            throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
        }finally {
        	//bufferedOutput.close();
		}
        System.out.println("-----------正常" + com.gt.manager.util.fileUpload.OssImageUploadUtil.OSS_GTWATER_URL + realPath + newFileName);
		return new DataMessage(DataMessage.RESULT_SUCESS,realPath + newFileName, "上传成功！");
    }
	
	
	
	
	/**
	 * UploadOss.putObject 参数列表
	 * InputStream in, 输入流
	 * long size,	      文件长度
	 * String dir,     目的地路径 (结尾加/)
	 * String filename, 文件名称
	 * int file_type    1：genghao-images   2：gtop
	 * 示例：
	 * putObject(new FileInputStream(new File("d:/data/test.jpg")), new File("d:/data/test.jpg").length(), "test/", "test.jpg", 1);
	 * return  boolen
	 * 查看图片域名 img.goola.cn 
	 * http://img.goola.cn/test/test.jpg
	 */
	public static void main(String[] args) throws Exception {
		
		File file=new File("C:\\Users\\Edianzu\\Desktop\\gp=0.jpg");
		System.out.println(UploadOss.putObject(new FileInputStream(file), file.length(), "test/", "1.png", 1));
	}
	
	
	

}
