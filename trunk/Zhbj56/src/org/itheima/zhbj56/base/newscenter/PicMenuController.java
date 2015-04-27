package org.itheima.zhbj56.base.newscenter;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.util.List;

import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.bean.NewsListPagerBean;
import org.itheima.zhbj56.bean.NewsListPagerBean.NewsItemBean;
import org.itheima.zhbj56.utils.Constans;
import org.itheima.zhbj56.utils.ImageHelper;

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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.newscenter
 * @类名: PicMenuController
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 下午2:16:49
 * @描述: 新闻中心中，组图菜单对应的控制器
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class PicMenuController extends MenuController implements OnClickListener
{

	// private TextView tv;

	@ViewInject(R.id.newscenter_pic_list_view)
	private ListView					mListView;

	@ViewInject(R.id.newscenter_pic_grid_view)
	private GridView					mGridView;

	private List<NewsItemBean>			mNewsPics;

	private BitmapUtils					mBitmapUtils;
	private ImageHelper					mHelper;

	private boolean						isGrid;		// 默认为list显示方式

	private PhantomReference<Bitmap>	mReference;

	public PicMenuController(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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

		View view = View.inflate(mContext, R.layout.newscenter_pic, null);

		// 注入
		ViewUtils.inject(this, view);

		// mBitmapUtils = new BitmapUtils(mContext);
		mHelper = new ImageHelper(mContext);

		return view;
	}

	@Override
	public void initData()
	{
		// 通过网络获取list数据
		HttpUtils utils = new HttpUtils();
		String url = Constans.PHOTOS_URL;
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// 获取网络结果
				String result = responseInfo.result;

				// 数据处理
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
	protected void processData(String json)
	{
		// 解析数据
		Gson gson = new Gson();
		NewsListPagerBean bean = gson.fromJson(json, NewsListPagerBean.class);
		mNewsPics = bean.data.news;

		// 设置数据
		PicAdapter adapter = new PicAdapter();// adapter ---> list
		mListView.setAdapter(adapter);
		mGridView.setAdapter(adapter);
	}

	class PicAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mNewsPics != null) { return mNewsPics.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mNewsPics != null) { return mNewsPics.get(position); }
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
				convertView = View.inflate(mContext, R.layout.item_pic, null);
				holder = new ViewHolder();
				// 设置标记
				convertView.setTag(holder);

				// view的初始化
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_pic_iv_icon);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.item_pic_tv_title);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			NewsItemBean bean = mNewsPics.get(position);

			// 填充数据
			holder.tvTitle.setText(bean.title);

			// 设置图片
			holder.ivIcon.setImageResource(R.drawable.pic_item_list_default);

			// 去网络获取图片
			// mBitmapUtils.display(holder.ivIcon, bean.listimage);

			mHelper.display(holder.ivIcon, bean.listimage);

			return convertView;
		}
	}

	class ViewHolder
	{
		ImageView	ivIcon;
		TextView	tvTitle;
	}

	public void setSwitchButton(ImageButton mIbListOrGrid)
	{
		// 设置click
		mIbListOrGrid.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// 如果是list显示，那么就变成grid，相反

		// 获取当前显示的模式
		isGrid = !isGrid;

		mListView.setVisibility(isGrid ? View.GONE : View.VISIBLE);
		mGridView.setVisibility(isGrid ? View.VISIBLE : View.GONE);

		// 图标改变
		((ImageButton) v).setImageResource(isGrid ? R.drawable.icon_pic_grid_type : R.drawable.icon_pic_list_type);
	}
}
