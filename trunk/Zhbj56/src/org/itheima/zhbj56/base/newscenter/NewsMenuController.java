package org.itheima.zhbj56.base.newscenter;

import java.util.List;

import org.itheima.zhbj56.MainUI;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsCenterBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsCenterMenuBean;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
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
public class NewsMenuController extends MenuController implements OnPageChangeListener
{

	// private TextView tv;
	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager			mPager;	// ViewPager

	@ViewInject(R.id.newscenter_news_indicator)
	private TabPageIndicator	mIndicator; // 指针

	private NewsCenterMenuBean	mMenuBean;	// 菜单数据

	private List<NewsBean>		mChildren;	// ViewPager对应的数据

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
			TextView tv = new TextView(mContext);
			tv.setText(mChildren.get(position).title);
			tv.setTextSize(24);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(Color.RED);
			container.addView(tv);

			return tv;
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

}
