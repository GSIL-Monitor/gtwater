package com.gt.manager;

import com.gt.manager.Constant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 贯通   </p>
 * <p>Description: 流读取工具类 </p>
 * <p>Author:/Stevehan, 16/09/20</p>
 */
public class IOReaderHelper {
	private static final Log log = LogFactory.getLog(IOReaderHelper.class);
	
	public static String ioReader(ServletRequest request) {
		String data = null;
		InputStream is = null;
		try {
			is = request.getInputStream();
			data = IOUtils.toString(is, Constant.ENCODING);
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		finally {
			IOUtils.closeQuietly(is);
		}
		return data;
	}
}
