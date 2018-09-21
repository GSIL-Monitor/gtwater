package com.gt.manager.branches;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.gt.manager.branches
 * @ClassName BranchesEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/2 16:30
 */
public class BranchesEntity implements Serializable {
    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private Long branchesId;//机构id
    private String branchesCode;//机构编号
    private String branchesName;//机构名称

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public String getBranchesCode() {
        return branchesCode;
    }

    public void setBranchesCode(String branchesCode) {
        this.branchesCode = branchesCode;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public void setBranchesName(String branchesName) {
        this.branchesName = branchesName;
    }


    public static void main(String[] args) {
         Logger log = Logger.getRootLogger();



        /*String str1 = "{\"resourceId\":\"dfead70e4ec5c11e43514000ced0cdcaf\",\"properties\":{\"process_id\":\"process4\",\"name\":\"\",\"documentation\":\"\",\"processformtemplate\":\"\"}}";
            String tmp = StringEscapeUtils.unescapeJavaScript(str1);
            System.out.println("tmp:" + tmp);*/

            Map<String,Object> map= new HashMap<>();
        map.put("name","wu");
        map.put("age",3);
       String ms= map.toString();
        System.out.println(ms);

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("nameJson","ceui");
        jsonObject.put("ageJson","18");
        JSONObject j2= new JSONObject();
        j2.put("j2",jsonObject);

        System.out.println("jsonToJsonString:"+j2.toJSONString());
        log.info("jsonToJsonString:"+j2.toJSONString());
        System.out.println("jsonToString:"+j2.toString());
        log.info("jsonToJsonString:"+j2.toJSONString());
        System.out.println("StringValueOf:"+String.valueOf(j2));
        log.info("jsonToJsonString:"+j2.toJSONString());
    }
}
