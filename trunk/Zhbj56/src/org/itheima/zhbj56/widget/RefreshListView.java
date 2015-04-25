package org.itheima.zhbj56.widget;

import org.itheima.zhbj56.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.widget
 * @类名: RefreshListView
 * @创建者: 肖琦
 * @创建时间: 2015-4-25 下午4:18:53
 * @描述: 自定义的刷新的view
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class RefreshListView extends ListView
{
	private static final String	TAG	= "RefreshListView";
	private LinearLayout		mHeaderLayout;				// 头布局(刷新部分 + 自定义部分)
	private View				mCustomHeaderView;			// 头布局中 自定义部分
	private View				mRefreshView;				// 头布局中刷新的部分

	public RefreshListView(Context context) {
		super(context);

		initHeaderLayout();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initHeaderLayout();
	}

	// 初始化头布局
	private void initHeaderLayout()
	{
		// 给ListView添加头布局
		// 加载头布局
		mHeaderLayout = (LinearLayout) View.inflate(getContext(), R.layout.refresh_header_layout, null);
		mRefreshView = mHeaderLayout.findViewById(R.id.refresh_header_refresh);

		// 添加到ListView的头布局中
		this.addHeaderView(mHeaderLayout);

		// 隐藏 刷新部分,设置头布局的paddingTop为刷新部分的高度的负数
		mRefreshView.measure(0, 0);
		int measuredHeight = mRefreshView.getMeasuredHeight();
		Log.d(TAG, "刷新部分的高度:" + measuredHeight);
		mHeaderLayout.setPadding(0, -measuredHeight, 0, 0);
	}

	public void addCustomHeaderView(View headerView)
	{
		this.mCustomHeaderView = headerView;
		mHeaderLayout.addView(headerView);
	}

}
