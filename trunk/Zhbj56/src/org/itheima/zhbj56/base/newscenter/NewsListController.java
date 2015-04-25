package org.itheima.zhbj56.base.newscenter;

import java.util.List;

import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsListPagerBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsBean;
import org.itheima.zhbj56.bean.NewsListPagerBean.NewsTopNewsBean;
import org.itheima.zhbj56.utils.Constans;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.newscenter
 * @类名: NewsListController
 * @创建者: 肖琦
 * @创建时间: 2015-4-25 上午10:11:41
 * @描述:新闻数据list页面对应的controller
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class NewsListController extends MenuController implements OnPageChangeListener
{

	private static final String		TAG	= "NewsListController";

	@ViewInject(R.id.news_list_pic_pager)
	private ViewPager				mPicPager;

	@ViewInject(R.id.news_list_tv_title)
	private TextView				mTvTitle;

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout			mPointContainer;

	private String					mUrl;

	private List<NewsTopNewsBean>	mPicDatas;

	private BitmapUtils				mBitmapUtils;

	public NewsListController(Context context, NewsBean data) {
		super(context);
		this.mUrl = data.url;
	}

	@Override
	protected View initView(Context context)
	{
		View view = View.inflate(mContext, R.layout.news_list_pager, null);

		// ViewUtils的注入
		ViewUtils.inject(this, view);

		mBitmapUtils = new BitmapUtils(mContext);

		return view;
	}

	@Override
	public void initData()
	{

		// 初始化Pager对应的List数据
		HttpUtils utils = new HttpUtils();
		String url = Constans.BASE_URL + mUrl;
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// 获得结果
				String result = responseInfo.result;

				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 处理数据
	 * 
	 * @param json
	 */
	private void processData(String json)
	{

		// json解析
		Gson gson = new Gson();
		NewsListPagerBean bean = gson.fromJson(json, NewsListPagerBean.class);
		mPicDatas = bean.data.topnews;

		// 去动态的添加点
		for (int i = 0; i < mPicDatas.size(); i++)
		{
			View point = new View(mContext);
			point.setBackgroundResource(R.drawable.dot_normal);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(6, 6);
			if (i != 0)
			{
				params.leftMargin = 8;
			}
			else
			{
				// 设置默认的图片
				point.setBackgroundResource(R.drawable.dot_focus);
			}
			// 添加点
			mPointContainer.addView(point, params);
		}

		// 给ViewPager初始化数据
		mPicPager.setAdapter(new TopPicPagerAdapter());// adapter --->
														// list

		// 添加ViewPager的监听
		mPicPager.setOnPageChangeListener(this);

		// 设置title的默认值
		mTvTitle.setText(mPicDatas.get(0).title);
	}

	class TopPicPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPicDatas != null) { return mPicDatas.size(); }
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
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);

			// 设置iv的image，设置默认值
			iv.setImageResource(R.drawable.home_scroll_default);

			// 设置网络图片
			String uri = mPicDatas.get(position).topimage;
			mBitmapUtils.display(iv, uri);

			container.addView(iv);

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
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
		// 当页面选中时的回调
		NewsTopNewsBean bean = mPicDatas.get(position);

		// 设置选中的点
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++)
		{
			View view = mPointContainer.getChildAt(i);
			view.setBackgroundResource(position == i ? R.drawable.dot_focus : R.drawable.dot_normal);
		}

		// 设置文本
		mTvTitle.setText(bean.title);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// TODO Auto-generated method stub

	}

}
