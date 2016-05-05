package org.itheima.zhbj56.utils;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.utils
 * @类名: Constans
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 上午10:07:17
 * @描述: 全局的常量类
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public interface Constans
{
	// String BASE_URL = "http://192.168.1.100:8080/zhbj";
	String	BASE_URL		= "http://10.0.2.2:8080/zhbj";

	/**
	 * 新闻中心的网络接口
	 */
	String	NEWSCENTER_URL	= BASE_URL + "/categories.json";

	/**
	 * 获取组图的接口
	 */

	String	PHOTOS_URL		= BASE_URL + "/photos/photos_1.json";
}
