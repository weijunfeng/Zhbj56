package org.itheima.zhbj56.fragment;

import java.util.ArrayList;
import java.util.List;

import org.itheima.zhbj56.BaseFragment;
import org.itheima.zhbj56.MainUI;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.TabController;
import org.itheima.zhbj56.base.tab.GovTabController;
import org.itheima.zhbj56.base.tab.HomeTabController;
import org.itheima.zhbj56.base.tab.NewsCenterTabController;
import org.itheima.zhbj56.base.tab.SettingTabController;
import org.itheima.zhbj56.base.tab.SmartServiceTabController;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.fragment
 * @类名: ContentFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:17:16
 * @描述: 主页的内容
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class ContentFragment extends BaseFragment implements OnCheckedChangeListener
{
	@ViewInject(R.id.content_pager)
	private ViewPager			mPager;		// ViewPager

	@ViewInject(R.id.content_rg)
	private RadioGroup			mRadioGroup;	// 底部的RadioGroup

	private List<TabController>	mPagerDatas;

	private int					mCurrentTab;

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("主页面");
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// return tv;

		View view = View.inflate(mActivity, R.layout.content, null);

		// 注入ViewUtils工具
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void initData()
	{
		// 数据初始化
		mPagerDatas = new ArrayList<TabController>();

		mPagerDatas.add(new HomeTabController(mActivity));// 首页
		mPagerDatas.add(new NewsCenterTabController(mActivity));// 新闻中心
		mPagerDatas.add(new SmartServiceTabController(mActivity));// 智慧服务
		mPagerDatas.add(new GovTabController(mActivity));// 政务
		mPagerDatas.add(new SettingTabController(mActivity));// 设置

		// 给ViewPager去加载数据
		mPager.setAdapter(new ContentPagerAdapter());// adapter ---> list<数据类型>

		// 给RadioGroup设置选中的监听
		mRadioGroup.setOnCheckedChangeListener(this);

		// 设置RadioGroup的默认选中值
		mCurrentTab = 0;
		mRadioGroup.check(R.id.content_rb_home);
	}

	class ContentPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPagerDatas != null) { return mPagerDatas.size(); }
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
			TabController controller = mPagerDatas.get(position);

			// 获得视图
			View rootView = controller.getRootView();
			container.addView(rootView);

			// 设置数据
			controller.initData();

			return rootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}

	// 1. RadioGroup本身
	// 2. 某一个选中的RadioButton的id
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId)
		{
			case R.id.content_rb_home:
				mCurrentTab = 0;
				// 设置菜单不可以滑动
				setSlidingMenuTouchEnable(false);
				break;
			case R.id.content_rb_news:
				mCurrentTab = 1;
				// 设置菜单可以滑动
				setSlidingMenuTouchEnable(true);
				break;
			case R.id.content_rb_smart:
				mCurrentTab = 2;
				// 设置菜单可以滑动
				setSlidingMenuTouchEnable(true);
				break;
			case R.id.content_rb_gov:
				mCurrentTab = 3;
				// 设置菜单可以滑动
				setSlidingMenuTouchEnable(true);
				break;
			case R.id.content_rb_setting:
				mCurrentTab = 4;
				// 设置菜单不可以滑动
				setSlidingMenuTouchEnable(false);
				break;
			default:
				break;
		}

		// 设置ViewPager的选中的页面
		mPager.setCurrentItem(mCurrentTab);
	}

	/**
	 * 设置滑动菜单是否可以滑动
	 */
	private void setSlidingMenuTouchEnable(boolean enable)
	{
		MainUI ui = (MainUI) mActivity;// 获取具体的宿主
		SlidingMenu menu = ui.getSlidingMenu();
		// if (enable)
		// {
		// // 设置touch可见
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// }
		// else
		// {
		// // 设置touch不可见
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// }
		menu.setTouchModeAbove(enable ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}

	public void switchMenu(int position)
	{
		TabController tabController = mPagerDatas.get(mCurrentTab);
		
		tabController.switchMenu(position);
	}
}
