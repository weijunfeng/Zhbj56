package org.itheima.zhbj56.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.widget
 * @类名: HorizontalViewPager
 * @创建者: 肖琦
 * @创建时间: 2015-4-25 上午11:15:14
 * @描述: 请求父容器不拦截touch的viewPager
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class HorizontalViewPager extends ViewPager
{

	private int	mDownX;

	public HorizontalViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();

		// 如果是第一个页面的时候，从左往右滑动， 希望打开菜单

		int position = getCurrentItem();

		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				requestDisallowInterceptTouchEvent(true);
				mDownX = (int) (ev.getX() + 0.5f);
				break;
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) (ev.getX() + 0.5f);

				int diffX = moveX - mDownX;

				// diffX > 0 从左往右
				// diffX < 0 从右往左
				// 当第一个页面显示时
				if (position == 0)
				{
					if (diffX > 0)
					{
						// 希望打开菜单,希望父容器响应touch，父去拦截touch
						requestDisallowInterceptTouchEvent(false);
					}
					else
					{
						// 从右往左，希望自己响应touch
						requestDisallowInterceptTouchEvent(true);
					}
				}
				else if (position > 0 && position < getAdapter().getCount() - 1)
				{
					// 当中间页面显示时，都希望自己响应touch
					requestDisallowInterceptTouchEvent(true);
				}
				else
				{
					// 最后一个页面
					// 如果从左往右滑动,希望看到上一个图片，自己响应touch
					if (diffX > 0)
					{
						requestDisallowInterceptTouchEvent(true);
					}
					else
					{
						// 让父容器响应
						requestDisallowInterceptTouchEvent(false);
					}

				}

				break;
			case MotionEvent.ACTION_UP:

				break;
			default:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
