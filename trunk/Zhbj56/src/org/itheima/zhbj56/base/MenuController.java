package org.itheima.zhbj56.base;

import android.content.Context;
import android.view.View;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base
 * @类名: MenuController
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 下午2:09:27
 * @描述: 菜单对应的控制器的基类
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public abstract class MenuController
{
	protected View		mRootView;
	protected Context	mContext;

	public MenuController(Context context) {
		this.mContext = context;
		mRootView = initView(context);
	}

	/**
	 * 初始化View
	 * 
	 * @return
	 */
	protected abstract View initView(Context context);

	/**
	 * 获得根视图
	 * 
	 * @return
	 */
	public View getRootView()
	{
		return mRootView;
	}

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public Context getContext()
	{
		return mContext;
	}

	/**
	 * 初始化数据的方法，孩子如果有数据初始化，就复写
	 */
	public void initData()
	{

	}
}
