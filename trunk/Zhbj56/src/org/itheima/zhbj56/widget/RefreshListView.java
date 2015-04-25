package org.itheima.zhbj56.widget;

import org.itheima.zhbj56.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
	private int					mDownX;
	private int					mDownY;
	private int					mRefreshHeight;

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
		mRefreshHeight = mRefreshView.getMeasuredHeight();
		Log.d(TAG, "刷新部分的高度:" + mRefreshHeight);
		mHeaderLayout.setPadding(0, -mRefreshHeight, 0, 0);
	}

	public void addCustomHeaderView(View headerView)
	{
		this.mCustomHeaderView = headerView;
		mHeaderLayout.addView(headerView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				mDownX = (int) (ev.getX() + 0.5f);
				mDownY = (int) (ev.getY() + 0.5f);
				break;
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) (ev.getX() + 0.5f);
				int moveY = (int) (ev.getY() + 0.5f);

				int diffX = moveX - mDownX;
				int diffY = moveY - mDownY;
				// 由上往下拉 diffY > 0

				// 如果第一个View是可见的情况下
				if (getFirstVisiblePosition() == 0)
				{
					if (diffY > 0)
					{
						Log.d(TAG, "第一个View可见");
						// 希望看到刷新的View
						// 改变头布局的PaddingTop
						mHeaderLayout.setPadding(0, diffY - mRefreshHeight, 0, 0);

						// 消费掉
						return true;
					}
				}

				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				break;
			default:
				break;
		}

		return super.onTouchEvent(ev);
	}

}
