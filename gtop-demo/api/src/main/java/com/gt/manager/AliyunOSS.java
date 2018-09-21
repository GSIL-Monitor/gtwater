package com.gt.manager;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @ClassName: AliyunOSS
 * @Description:文件上传到阿里云服务器
 * @author wgl
 * @date 2016年8月31日 下午12:42:22
 */
@SuppressWarnings("all")
public class AliyunOSS {

	/**
	 * @Title: uploadQrFile
	 * @Description:上传文件地址
	 * @param key 文件名字
	 * @param file 文件流
	 * @return
	 */
	public static String uploadQrFile(String name, InputStream file) {
		// 使用默认的OSS服务器地址创建OSSClient对象。
		OSSClient client = new OSSClient(AliyunConstants.ALIYUN_OSS_ENDPOINT,AliyunConstants.ALIYUN_OSS_ACCESS_ID, AliyunConstants.ALIYUN_OSS_ACCESS_KEY);
		try {
			uploadFileQr(client, AliyunConstants.ALIYUN_OSS_BUCKET_NAME, name, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AliyunConstants.ALIYUN_QR_URL + name;
	}

	// 上传文件
	private static void uploadFile(OSSClient client, String bucketName, String key, String filename)
			throws OSSException, ClientException, FileNotFoundException {
		File file = new File(filename);
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType("image/jpeg");
		InputStream input = new FileInputStream(file);
		client.putObject(bucketName, key, input, objectMeta);
	}

	// 上传文件
	private static void uploadFileQr(OSSClient client, String bucketName, String key, InputStream in) throws Exception {
		ObjectMetadata objectMeta = new ObjectMetadata();
		// objectMeta.setContentLength();
		// 可以在metadata中标记文件类型
		// objectMeta.setContentType("image/jpeg");
		client.putObject(bucketName, key, in, objectMeta);
	}
	public static void main(String[] args) throws  Exception
	{
		String path=uploadQrFile("barcode/dev/code.jpg",new FileInputStream(new File("d:\\code.jpg")));
		System.out.println(path);
	}
}
