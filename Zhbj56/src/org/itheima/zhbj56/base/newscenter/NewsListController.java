package org.itheima.zhbj56.base.newscenter;

import java.util.List;

import org.itheima.zhbj56.DetailUI;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsBean;
import org.itheima.zhbj56.bean.NewsListPagerBean;
import org.itheima.zhbj56.bean.NewsListPagerBean.NewsItemBean;
import org.itheima.zhbj56.bean.NewsListPagerBean.NewsTopNewsBean;
import org.itheima.zhbj56.utils.CacheUtils;
import org.itheima.zhbj56.utils.Constans;
import org.itheima.zhbj56.widget.RefreshListView;
import org.itheima.zhbj56.widget.RefreshListView.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

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
public class NewsListController extends MenuController implements OnPageChangeListener, OnRefreshListener, OnItemClickListener
{

	private static final String		TAG	= "NewsListController";

	@ViewInject(R.id.news_list_pic_pager)
	private ViewPager				mPicPager;

	@ViewInject(R.id.news_list_tv_title)
	private TextView				mTvTitle;

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout			mPointContainer;

	@ViewInject(R.id.news_list_view)
	private RefreshListView			mListView;

	private String					mUrl;

	private List<NewsTopNewsBean>	mPicDatas;

	private BitmapUtils				mBitmapUtils;

	private AutoSwitchPicTask		mAutoSwitchTask;

	private List<NewsItemBean>		mNewsDatas;				// Adatper对应的数据

	private String					mMoreUrl;

	private ListDataAdapter			mNewsAdapter;

	public NewsListController(Context context, NewsBean data) {
		super(context);
		this.mUrl = data.url;
	}

	@Override
	protected View initView(Context context)
	{
		mBitmapUtils = new BitmapUtils(mContext);

		View view = View.inflate(mContext, R.layout.news_list_pager, null);

		// ViewUtils的注入
		ViewUtils.inject(this, view);

		// 加载轮播图对应的View
		View picLayout = View.inflate(mContext, R.layout.news_top_pic, null);
		// ViewUtils注入
		ViewUtils.inject(this, picLayout);

		// 给ListView加载自定义的头布局
		mListView.addCustomHeaderView(picLayout);

		// 设置刷新监听
		mListView.setOnRefreshListener(this);

		// 设置item的监听
		mListView.setOnItemClickListener(this);

		return view;
	}

	@Override
	public void initData()
	{

		// 初始化Pager对应的List数据
		final String url = Constans.BASE_URL + mUrl;

		// 获取缓存
		String json = CacheUtils.getString(mContext, url);
		if (!TextUtils.isEmpty(json))
		{
			processData(json);
		}

		getDataFromNet(url, false);
	}

	// 从网络获取数据
	private void getDataFromNet(final String url, final boolean refresh)
	{
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{

				// 获得结果
				String result = responseInfo.result;

				Log.d(TAG, "数据加载成功 ：" + result);

				// 设置缓存
				CacheUtils.setString(mContext, url, result);

				mListView.setRereshTime(System.currentTimeMillis());

				processData(result);

				if (refresh)
				{
					// 通知ListView刷新完成
					mListView.setRefreshFinish();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				if (refresh)
				{
					// 通知ListView刷新完成
					mListView.setRefreshFinish();

					Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
				}
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
		mNewsDatas = bean.data.news;
		mMoreUrl = bean.data.more;

		// 清空点
		mPointContainer.removeAllViews();
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

		// 开启轮播图
		if (mAutoSwitchTask == null)
		{
			mAutoSwitchTask = new AutoSwitchPicTask();
		}
		mAutoSwitchTask.start();

		// 设置ViewPager的touch的监听
		mPicPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{

				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						Log.d(TAG, "按下去，停止轮播");
						// 如果手指按下去时，希望轮播停止，
						mAutoSwitchTask.stop();
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
						Log.d(TAG, "抬起，开始轮播");
						// 如果手指抬起时，图片进行轮播
						mAutoSwitchTask.start();
						break;
					default:
						break;
				}

				return false;
			}
		});

		// 加载listView的数据
		mNewsAdapter = new ListDataAdapter();
		mListView.setAdapter(mNewsAdapter);// adapter --->List

	}

	class ListDataAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mNewsDatas != null) { return mNewsDatas.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mNewsDatas != null) { return mNewsDatas.get(position); }
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				// 没有复用
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.item_news, null);
				// 设置tag
				convertView.setTag(holder);
				// 初始化view
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_news_iv_icon);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.item_news_tv_title);
				holder.tvPubDate = (TextView) convertView.findViewById(R.id.item_news_tv_pubdate);
			}
			else
			{
				// 复用
				holder = (ViewHolder) convertView.getTag();
			}

			NewsItemBean bean = mNewsDatas.get(position);

			// 设置数据
			holder.tvTitle.setText(bean.title);
			holder.tvPubDate.setText(bean.pubdate);

			// 设置图片的默认值
			holder.ivIcon.setImageResource(R.drawable.pic_item_list_default);
			// 去网络加载图片
			mBitmapUtils.display(holder.ivIcon, bean.listimage);

			// 判断是否有缓存
			boolean cache = CacheUtils.getBoolean(mContext, bean.id + "");
			holder.tvTitle.setTextColor(cache ? Color.GRAY : Color.BLACK);

			return convertView;
		}
	}

	class ViewHolder
	{
		ImageView	ivIcon;
		TextView	tvTitle;
		TextView	tvPubDate;
	}

	class AutoSwitchPicTask extends Handler implements Runnable
	{
		/**
		 * 开启任务
		 */
		public void start()
		{
			stop();
			postDelayed(this, 2000);
		}

		/**
		 * 关闭任务
		 */
		public void stop()
		{
			removeCallbacks(this);
		}

		@Override
		public void run()
		{
			// ViewPager选中下一个，如果是最后一个就选中第一个

			int position = mPicPager.getCurrentItem();
			if (position != mPicPager.getAdapter().getCount() - 1)
			{
				// 选中下一个
				mPicPager.setCurrentItem(++position);
			}
			else
			{
				// 如果是最后一个就选中第一个
				mPicPager.setCurrentItem(0);
			}

			// 发送延时任务
			postDelayed(this, 2000);
		}

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

	@Override
	public void onRefreshing()
	{
		// 正在刷新中
		Log.d(TAG, "回调了正在刷新");

		// 重新请求当前页面的数据
		final String url = Constans.BASE_URL + mUrl;
		getDataFromNet(url, true);

	}

	@Override
	public void onLoadMore()
	{
		if (TextUtils.isEmpty(mMoreUrl))
		{
			// 没有更多数据
			Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();

			// 设置加载完成,没有更多
			mListView.setRefreshFinish(true);

			return;
		}

		// 加载更多的回调
		HttpUtils utils = new HttpUtils();
		String url = Constans.BASE_URL + mMoreUrl;
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// 获取更多的数据
				String result = responseInfo.result;

				// 解析json
				Gson gson = new Gson();
				NewsListPagerBean bean = gson.fromJson(result, NewsListPagerBean.class);
				List<NewsItemBean> news = bean.data.news;
				// 设置moreUrl
				mMoreUrl = bean.data.more;
				// 追加到List数据中
				mNewsDatas.addAll(news);
				// UI更新,listView对应的adapter 进行更新
				mNewsAdapter.notifyDataSetChanged();

				// 设置加载完成
				mListView.setRefreshFinish();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// 设置加载完成
				mListView.setRefreshFinish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (mNewsDatas == null) { return; }

		position = position - 1;
		Log.d(TAG, "点击的位置" + position);

		if (position >= mNewsDatas.size()) { return; }

		NewsItemBean bean = mNewsDatas.get(position);
		long newsId = bean.id;
		// 缓存已读的数据
		CacheUtils.setBoolean(mContext, "" + newsId, true);
		// 通知UI刷新
		mNewsAdapter.notifyDataSetChanged();

		// 页面跳转
		Intent intent = new Intent(mContext, DetailUI.class);
		intent.putExtra(DetailUI.KEY_URL, bean.url);
		mContext.startActivity(intent);
	}
}
