package org.itheima.zhbj56.fragment;

import java.util.ArrayList;
import java.util.List;

import org.itheima.zhbj56.BaseFragment;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.TabController;
import org.itheima.zhbj56.base.tab.GovTabController;
import org.itheima.zhbj56.base.tab.HomeTabController;
import org.itheima.zhbj56.base.tab.NewsCenterTabController;
import org.itheima.zhbj56.base.tab.SettingTabController;
import org.itheima.zhbj56.base.tab.SmartServiceTabController;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
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
public class ContentFragment extends BaseFragment
{
	@ViewInject(R.id.content_pager)
	private ViewPager			mPager;		// ViewPager

	private List<TabController>	mPagerDatas;

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

}
