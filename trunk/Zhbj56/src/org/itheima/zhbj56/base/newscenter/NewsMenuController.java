package org.itheima.zhbj56.base.newscenter;

import java.util.List;

import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsCenterBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsCenterMenuBean;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
public class NewsMenuController extends MenuController
{

	// private TextView tv;
	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager			mPager;	// ViewPager

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

	}

}
