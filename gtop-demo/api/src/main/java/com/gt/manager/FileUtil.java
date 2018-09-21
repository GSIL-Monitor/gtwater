package com.gt.manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description:  </p>
 * <p>Author:xiaochangdong, 2017/3/29</p>
 */
public class FileUtil {
    /**
     * <p>Description: 创建目录</p>
     * <p> * @param null  </p>
     * <p>author ningxiaoling </p>
     * <p>date 2017/3/24 13:11 </p>
     * <p>return </p>
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            deleteDir(new File(destDirName));
        }
        if (!destDirName.endsWith(File.separator)) {
            // 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }
    /**
     * <p>Description: 删除单个文件夹</p>
     * <p> * @param null  </p>
     * <p>author ningxiaoling </p>
     * <p>date 2017/3/24 10:42 </p>
     * <p>return </p>
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
    * <p>Description: 读入文件放入Treeset</p>
    * <p> * @param null</p>
    * <p>author xiaochangdong </p>
    * <p>date 2017/3/30 11:15 </p>
    * <p>return </p>
    */
    public static List<String> readFileByLines(String fileName) throws IOException{
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        //创建一个集合
       List<String> list = new ArrayList<String>();
        //按行读取文件内容，并存放到集合
        while ((tempString = reader.readLine()) != null){
            list.add(tempString);
        }
        reader.close();
        //返回集合变量
        return list;
    }

    /**
    * <p>Description: 写入文件</p>
    * <p> * @param null</p>
    * <p>author xiaochangdong </p>
    * <p>date 2017/3/30 11:04 </p>
    * <p>return </p>
    */
    public static void fileWriter(String fileName,List<String> clist) throws IOException{
        //创建一个FileWriter对象
        FileWriter fw = new FileWriter(fileName);
        //遍历clist集合写入到fileName中
        for (String str: clist){
            fw.write(str);
            fw.write("\n");
        }
        //刷新缓冲区
        fw.flush();
        //关闭文件流对象
        fw.close();
    }

    /**
    * <p>Description: 获取文件夹大小</p>
    * <p>author xiaochangdong </p>
    * <p>date 2017/4/20 16:21 </p>
    * <p>return </p>
    */
    public static long getTotalSizeOfFilesInDir(String fileName) {
        File file = new File(fileName);
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child.getAbsolutePath());
        return total;
    }
}
