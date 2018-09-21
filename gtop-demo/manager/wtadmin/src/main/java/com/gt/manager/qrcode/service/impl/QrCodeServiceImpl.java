package com.gt.manager.qrcode.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.HttpUtils;
import com.gt.manager.entity.wtExtensioncode.WtExtensioncode;
import com.gt.manager.qrcode.repository.WtExtensioncodeDAO;
import com.gt.manager.qrcode.service.QrCodeService;
import com.gt.manager.qrcode.utils.QrCodeGenerateUtil;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(value = "qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private static HttpServletRequest request;
    @Value("${qrcode.url}")
    private String qrCodeUrl;

    @Value("${qrcode.type}")
    private String qrCodeType;

    @Value("${qrcode.transformationUrl}")
    private String transformationUrl;

    @Autowired
    private WtExtensioncodeDAO wtExtensioncodeDAO;



    /**
     * 展示二维码列表
     * @return
     * @param jsonObject
     */
    @Override
    public DataMessage displayDataToQrcode(JSONObject jsonObject) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //时间转毫秒
            if(jsonObject.containsKey("startTime") && !StringUtils.isEmpty("startTime")){
                Date startTime = formatter.parse(jsonObject.getString("startTime"));
                jsonObject.put("startTime", startTime.getTime());
            }
            if(jsonObject.containsKey("endTime") && !StringUtils.isEmpty("endTime")){
                Date endTime = formatter.parse(jsonObject.getString("endTime"));
                jsonObject.put("endTime", endTime.getTime());
            }
            //查询信息列表
            PageHelper.startPage(jsonObject.getIntValue("pageNo"), jsonObject.getIntValue("pageSize"));
            Map map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
            List<WtExtensioncode> list = wtExtensioncodeDAO.selectDataToMap(map);
            PageInfo<WtExtensioncode> infoList = new PageInfo<>(list);
            return new DataMessage(DataMessage.RESULT_SUCESS, infoList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    /**
     * 生成 pdf 格式的二维码
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage qrCodeGenerate(JSONObject jsonObject) {
        WtExtensioncode wtExtensioncode = new WtExtensioncode();
        int count = 0;
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空，请检查参数");
            }
            if(jsonObject.containsKey("count") && !StringUtils.isEmpty(jsonObject.getString("count"))){
                count = jsonObject.getIntValue("count");
            }else{
                return new DataMessage(DataMessage.RESULT_FAILED, null, "二维码数量为空");
            }
            if(!jsonObject.containsKey("userId") || StringUtils.isEmpty(jsonObject.getString("userId"))){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "用户id为空");
            }
            //生成二维码
            //写到pdf 中
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            String path = this.getClass().getClassLoader().getResource("qrcode/code.pdf").getPath();
            String codePath = this.getClass().getClassLoader().getResource("qrcode/").getPath();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Chapter chapter1 = new Chapter(1);
            chapter1.setNumberDepth(0);
            String message = "", url = "", resposeResult = "", codeUrl = "";
            JSONObject object = null;
            JSONObject resultObj = null;
            for(int i = 0; i < count; i++){
                message = String.valueOf(System.nanoTime());
                url = qrCodeUrl+message+"#"+qrCodeType;
                System.out.println("url:" + transformationUrl);
                object = new JSONObject();
                object.put("url", url);
                resposeResult = HttpUtils.post(transformationUrl, null, object);
                System.out.println("返回结果：" + resposeResult);
                resultObj = JSONObject.parseObject(resposeResult);
                BufferedImage bim = null;
                if(resultObj != null && resultObj.containsKey("result")){
                    if(resultObj.getIntValue("result") == 0){
                        codeUrl = resultObj.getString("data");
                        System.out.println("返回的数据:" + codeUrl);
                        bim = QrCodeGenerateUtil.createCode(codeUrl, 400, 400);
                    }
                }

                /**
                 * 读取二维码图片，并构建绘图对象
                 */
                BufferedImage image = bim;
                Graphics2D g = image.createGraphics();

                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //画二维码到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //画文字到新的面板
                outg.setColor(Color.BLACK);
                outg.setFont(new Font("宋体",Font.CENTER_BASELINE,30)); //字体、字型、字号
                int strWidth = outg.getFontMetrics().stringWidth(message);
                if (strWidth > 299) {
                    //长度过长就换行
                    String productName1 = message.substring(0, message.length()/2);
                    String productName2 = message.substring(message.length()/2, message.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 15 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK);
                    outg2.setFont(new Font("宋体",Font.CENTER_BASELINE,30)); //字体、字型、字号
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 10 );
                    outg2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(message, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字
                }
                outg.dispose();
                outImage.flush();
                image = outImage;
                image.flush();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.flush();
                ImageIO.write(image, "png", baos);
                ImageIO.write(image, "png", new File(codePath + message +".png")); //TODO

                com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance(codePath + message +".png");
                image2.scaleAbsolute(120f, 120f);
                image2.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

                chapter1.add(image2);
                //删除图片
                File f=new File(codePath + message +".png");//此时就可得到文件夹中的文件
                f.delete();
                //添加数据
                wtExtensioncode = new WtExtensioncode();
                wtExtensioncode.setMessages(message);
                wtExtensioncode.setUrl(codeUrl);
                wtExtensioncode.setCreateTime(System.currentTimeMillis());
                wtExtensioncode.setCreateId(jsonObject.getLongValue("userId"));
                wtExtensioncode.setDelState(1);
                wtExtensioncodeDAO.insertSelective(wtExtensioncode);
            }
            document.add(chapter1);
            document.close();
            return new DataMessage(DataMessage.RESULT_SUCESS, path, "成功生成二维码");
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    /**
     * 批量生成二维码
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage piLiangqrCodeGenerate(JSONObject jsonObject) {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            //根据 ids 查询消息
            if(jsonObject.containsKey("ids") && !StringUtils.isEmpty(jsonObject.getString("ids"))) {
                String ids = jsonObject.getString("ids");
                String[] split = ids.split(",");
                jsonObject.put("codeIdList", Arrays.asList(split));
                Map map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
                List<WtExtensioncode> codeData = wtExtensioncodeDAO.selectDataToMap(map);
                //生成二维码
                //写到pdf 中
                Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                String path = this.getClass().getClassLoader().getResource("qrcode/code.pdf").getPath();
                String codePath = this.getClass().getClassLoader().getResource("qrcode/").getPath();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();
                Chapter chapter1 = new Chapter(1);
                chapter1.setNumberDepth(0);

                for (WtExtensioncode  code : codeData){
                    String message = code.getMessages();
                    String url = code.getUrl();
                    BufferedImage bim = QrCodeGenerateUtil.createCode(url, 400, 400);
                    /**
                     * 读取二维码图片，并构建绘图对象
                     */
                    BufferedImage image = bim;
                    Graphics2D g = image.createGraphics();

                    BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg = outImage.createGraphics();
                    //画二维码到新的面板
                    outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                    //画文字到新的面板
                    outg.setColor(Color.BLACK);
                    outg.setFont(new Font("宋体",Font.CENTER_BASELINE,30)); //字体、字型、字号
                    int strWidth = outg.getFontMetrics().stringWidth(message);
                    if (strWidth > 299) {
                        //长度过长就换行
                        String productName1 = message.substring(0, message.length()/2);
                        String productName2 = message.substring(message.length()/2, message.length());
                        int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                        int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                        outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 15 );
                        BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                        Graphics2D outg2 = outImage2.createGraphics();
                        outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                        outg2.setColor(Color.BLACK);
                        outg2.setFont(new Font("宋体",Font.CENTER_BASELINE,30)); //字体、字型、字号
                        outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 10 );
                        outg2.dispose();
                        outImage2.flush();
                        outImage = outImage2;
                    }else {
                        outg.drawString(message, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字
                    }
                    outg.dispose();
                    outImage.flush();
                    image = outImage;
                    image.flush();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos.flush();
                    ImageIO.write(image, "png", baos);
                    ImageIO.write(image, "png", new File(codePath + message +".png")); //TODO

                    com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance(codePath + message +".png");
                    image2.scaleAbsolute(120f, 120f);
                    image2.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

                    chapter1.add(image2);
                    //删除图片
                    File f=new File(codePath + message +".png");//此时就可得到文件夹中的文件
                    f.delete();
                }
                document.add(chapter1);
                document.close();
                return new DataMessage(DataMessage.RESULT_FAILED, path, "生成二维码成功");
            } else{
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,主键id为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    /**
     * 批量删除
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage deleteCodeById(JSONObject jsonObject) {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "系统异常");
            }
            if(jsonObject.containsKey("ids") && !StringUtils.isEmpty(jsonObject.getString("ids"))){
                jsonObject.put("delState", 0);
                String ids = jsonObject.getString("ids");
                String[] split = ids.split(",");
                jsonObject.put("codeIdList", Arrays.asList(split));
                Map map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
                wtExtensioncodeDAO.updateServlet(map);
                return new DataMessage(DataMessage.RESULT_SUCESS, null, "删除成功");
            }else{
                return new DataMessage(DataMessage.RESULT_FAILED, null, "用户id为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }
}
