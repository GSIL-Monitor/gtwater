package com.gt.manager.user.repository.wtProductDAO;

//import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构_商品_价格表
 * @author why
 */
@Mapper
public interface WtProductDAO {

	/**
	 *   根据水站ID、状态、类别、品牌 来查询商品信息
	 */
	 List<Map<String,Object>> selectByIds(JSONObject json);

	/**
	 * 根据水站ID、状态、类别、品牌 来查询套餐信息
	 */
	List<Map<String,Object>> selectSetmeal(JSONObject json);

	/**
	 * 根据水站ID 获取banner图
	 */
	List<Map<String,Object>> selectBanner();
	/**
	 * 根据用户id 查找用户头像和昵称
	 */
	HashMap<String,Object> selectUserNickNameIcon(HashMap<String,Object> has);

	/**
	 * 根据用户id 修改用户头像和昵称
	 */
	Long updateUserNickNameIcon(HashMap<String,Object> has);
	/**
	 * 查询用户默认地址
	 */
	Map<String,Object> selectUserAddress(HashMap<String,Object> has);
	/**
	 * 修改用户默认地址
	 */
	Long updateUserAddress(HashMap<String,Object> has);
	/**
	 * 添加用户默认地址
	 */
	Long saveUserAddress(HashMap<String,Object> has);
	/**
	 * 查找品牌
	 */
	 List<Map<String,Object>>  queryCategory(HashMap<String,Object> has);

	/**
	 * 查找品牌
	 */
		List<Map<String,Object>>  queryBrand(HashMap<String,Object> has);

	/**
	 * 水站ID
	 */
		HashMap<String,Object> queryWaterstoreId(HashMap<String,Object> has);

	/**
	 * 通过套系编号查找赠品
	 */
		List<Map<String,Object>>  queryGift(Map<String,Object> has);

	/**
	 * 通过skucode和水站ID 查找商品详情
	 */
	Map<String,Object> queryProductMes(Map<String,Object> has);

	/**
	 * 查询商品信息生成商品序列
	 */
	Map<String,Object>  selectProducts(Map <String, Object> map);
}