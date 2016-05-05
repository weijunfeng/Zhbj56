package org.itheima.zhbj56.base;

import org.itheima.zhbj56.MainUI;
import org.itheima.zhbj56.R;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
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
public abstract class TabController implements OnClickListener
{
	protected View			mRootView;
	protected Context		mContext;

	protected ImageButton	mIbMenu;
	protected ImageButton	mIbListOrGrid;
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
		mIbListOrGrid = (ImageButton) view.findViewById(R.id.tab_ib_list_or_grid);
		mTvTitle = (TextView) view.findViewById(R.id.tab_tv_title);
		mContentContainer = (FrameLayout) view.findViewById(R.id.tab_container_content);

		// 初始化内容的View
		mContentContainer.addView(initContentView(context));

		// 设置菜单按钮的点击监听
		mIbMenu.setOnClickListener(this);

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

	@Override
	public void onClick(View v)
	{
		if (v == mIbMenu)
		{
			clickMenu();
		}
	}

	private void clickMenu()
	{
		// 点击时打开或是关闭菜单
		SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();
		menu.toggle();// 如果菜单是打开的时候就关闭，否则相反
	}

	/**
	 * 用来切换菜单的方法,如果子类有菜单，那么就复写此方法
	 * 
	 * @param position
	 */
	public void switchMenu(int position)
	{
		// TODO Auto-generated method stub

	}
}
