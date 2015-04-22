package org.itheima.zhbj56;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56
 * @类名: GuideUI
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 上午10:41:15
 * @描述: 引导页面
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class GuideUI extends Activity
{
	private ViewPager		mPager;	// 页面中的Viewpager

	private List<ImageView>	mPageDatas; // 页面对应的数据

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide);

		// 初始化View
		initView();

		// 初始化数据
		initData();
	}

	private void initView()
	{
		mPager = (ViewPager) findViewById(R.id.guide_pager);
	}

	private void initData()
	{
		int[] imgRes = new int[] {
				R.drawable.guide_1,
				R.drawable.guide_2,
				R.drawable.guide_3
		};

		// 初始化List数据
		mPageDatas = new ArrayList<ImageView>();

		ImageView iv;
		for (int i = 0; i < imgRes.length; i++)
		{
			iv = new ImageView(this);
			iv.setImageResource(imgRes[i]);// 设置图片资源
			iv.setScaleType(ScaleType.FIT_XY);// 设置图片填充
			// 添加到list中
			mPageDatas.add(iv);
		}

		// 给ViewPager设置数据
		mPager.setAdapter(new GuideAdapter());// adapter --> list<数据类型>

	}

	class GuideAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPageDatas != null) { return mPageDatas.size(); }
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
			// 展示ImageView
			ImageView iv = mPageDatas.get(position);

			// 将ImageView加到ViewPager中
			container.addView(iv);

			// 返还ImageView
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}
}
