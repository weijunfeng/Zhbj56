package org.itheima.zhbj56.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.utils
 * @类名: CacheUtils
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 上午10:32:52
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class CacheUtils
{
	private final static String	SP_NAME	= "zhbj56";

	/**
	 * 通过SP获得boolean类型的数据，没有默认为false
	 * 
	 * @param context
	 *            : 上下文
	 * @param key
	 *            : 存储的key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}

	/**
	 * 通过SP获得boolean类型的数据，没有默认为false
	 * 
	 * @param context
	 *            : 上下文
	 * @param key
	 *            : 存储的key
	 * @param defValue
	 *            : 默认值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue)
	{
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
}
