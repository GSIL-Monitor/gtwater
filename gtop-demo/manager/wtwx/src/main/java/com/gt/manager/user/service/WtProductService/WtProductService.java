package com.gt.manager.user.service.WtProductService;


//import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 水站商品sku表
 * @author why
 */
public interface WtProductService {


	/**
	 * 根据水站ID、状态、类别、品牌 来查询商品信息
	 */
	 Map<String,Object> selectproduct(JSONObject json);

	/**
	 * 根据水站ID获取水站banner图
	 */
	Map<String, Object> selectBanner() throws Exception;
	/**
	 * 获取水站范围等信息
	 */
	Map <String, Object> getListBySceneIdInLocation(Long orgId, Integer sceneId, String address) throws Exception;
	/**
	 * 根据用户id 查找用户头像和昵称
	 */
	Map<String,Object> selectUserNickNameIcon(Long id);
	/**
	 * 根据用户id 修改用户头像和昵称
	 */
	Long UpdateUserNickNameIcon(String id,String nickname,String icon);
	/**
	 * 根据用户id 用户默认地址
	 */
	Map<String,Object> selectUserAddress(HashMap<String,Object> has);
	/**
	 * 添加用户默认地址
	 */
	Long saveUserAddress(HashMap<String,Object> has);
	/**
	 * 查找品牌类目
	 */
	Map<String, Object> queryBrandCategory(HashMap<String,Object> has);

	/**
	 * 通过skucode和水站ID 查找商品详情
	 */
	Map<String,Object> queryProductMes(Map<String,Object> has);
}