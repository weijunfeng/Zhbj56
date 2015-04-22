package org.itheima.zhbj56.base;

import org.itheima.zhbj56.R;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base
 * @类名: TabController
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午4:36:02
 * @描述: tab页面对应的控制器
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public abstract class TabController
{
	protected View			mRootView;
	protected Context		mContext;

	protected ImageButton	mIbMenu;
	protected TextView		mTvTitle;
	protected FrameLayout	mContentContainer;

	public TabController(Context context) {
		this.mContext = context;
		mRootView = initView(context);
	}

	/**
	 * 初始化试图
	 * 
	 * @return
	 */
	private View initView(Context context)
	{
		View view = View.inflate(mContext, R.layout.base_tab, null);

		mIbMenu = (ImageButton) view.findViewById(R.id.tab_ib_menu);
		mTvTitle = (TextView) view.findViewById(R.id.tab_tv_title);
		mContentContainer = (FrameLayout) view.findViewById(R.id.tab_container_content);

		// 初始化内容的View
		mContentContainer.addView(initContentView(context));

		return view;
	}

	/**
	 * 初始化内容的view
	 * 
	 * @return
	 */
	protected abstract View initContentView(Context context);

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
