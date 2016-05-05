package org.itheima.zhbj56.base.newscenter;

import java.util.List;

import org.itheima.zhbj56.MainUI;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsCenterBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsCenterMenuBean;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnSlidingMenuTouchingListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.newscenter
 * @类名: NewsMenuController
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 下午2:14:01
 * @描述: 新闻中心中，新闻菜单对应的控制器
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class NewsMenuController extends MenuController implements OnPageChangeListener, OnSlidingMenuTouchingListener
{

	private static final String	TAG	= "NewsMenuController";

	// private TextView tv;
	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager			mPager;					// ViewPager

	@ViewInject(R.id.newscenter_news_indicator)
	private TabPageIndicator	mIndicator;				// 指针

	private NewsCenterMenuBean	mMenuBean;					// 菜单数据

	private List<NewsBean>		mChildren;					// ViewPager对应的数据

	public NewsMenuController(Context context, NewsCenterMenuBean menuBean) {
		super(context);

		this.mMenuBean = menuBean;
		mChildren = menuBean.children;
	}

	@Override
	protected View initView(Context context)
	{
		// tv = new TextView(context);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		// tv.setTextColor(Color.RED);
		//
		// return tv;

		View view = View.inflate(mContext, R.layout.newscenter_news, null);

		// 注入ViewUtils工具
		ViewUtils.inject(this, view);

		// 代码的版本适配
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			// 如果小于3.0的版本，才监听
			// 添加 SlidingMenu的滑动监听
			SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();
			menu.setOnSlidingMenuTouchingListener(this);
		}

		return view;
	}

	@Override
	public void initData()
	{
		// 设置实体数据
		// tv.setText("新闻菜单对应的页面");

		// adapter ---> List
		mPager.setAdapter(new NewsPagerAdapter());

		// 给指针设置ViewPager
		mIndicator.setViewPager(mPager);

		// 对ViewPager的pager改变进行监听
		mIndicator.setOnPageChangeListener(this);
	}

	@OnClick(R.id.newscenter_news_arrow)
	public void clickArrow(View view)
	{
		// 点击箭头
		// 选中下一个
		int item = mPager.getCurrentItem();
		mPager.setCurrentItem(++item);
	}

	class NewsPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mChildren != null) { return mChildren.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			// TextView tv = new TextView(mContext);
			// tv.setText(mChildren.get(position).title);
			// tv.setTextSize(24);
			// tv.setGravity(Gravity.CENTER);
			// tv.setTextColor(Color.RED);
			// container.addView(tv);
			// return tv;
			NewsBean bean = mChildren.get(position);

			NewsListController controller = new NewsListController(mContext, bean);

			// 获取View
			View rootView = controller.getRootView();

			// 将View添加到ViewPager中
			container.addView(rootView);

			// 给controller初始化数据
			controller.initData();

			return rootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		// 返回ViewPager对应的title值
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mChildren != null)
			{
				NewsBean bean = mChildren.get(position);
				return bean.title;
			}
			return super.getPageTitle(position);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position)
	{
		SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();

		// // 页面选中时的回调
		// if (position == 0)
		// {
		// // 希望可以打开菜单
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// }
		// else
		// {
		// // 不希望打开菜单
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// }

		menu.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouching(boolean isTouching, MotionEvent ev)
	{
		SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();
		int position = mPager.getCurrentItem();

		// 当触摸的时候,如果点在Indicator上就不打开slidingMenu
		if (isTouching)
		{
			float rawX = ev.getRawX();// 自己相对屏幕的
			float rawY = ev.getRawY();// 自己相对屏幕的

			// 输出函数
			int[] lw = new int[2];// [0] x，[1] y
			// 获取indicator左上角的点的坐标
			mIndicator.getLocationInWindow(lw);

			int releatviX = (int) (rawX - lw[0] + 0.5f);
			int releatviY = (int) (rawY - lw[1] + 0.5f);

			// 输出函数
			Rect outRect = new Rect();
			mIndicator.getHitRect(outRect);

			if (outRect.contains(releatviX, releatviY))
			{
				Log.d(TAG, "点击了IndicatorView");
				// 不打开slidingMenu
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}
			else
			{
				Log.d(TAG, "没有点击了IndicatorView");

				// 如果当前选中的是第0个
				menu.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
			}

		}
	}

}
