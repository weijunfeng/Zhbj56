package org.itheima.zhbj56.base.tab;

import java.util.ArrayList;
import java.util.List;

import org.itheima.zhbj56.MainUI;
import org.itheima.zhbj56.base.MenuController;
import org.itheima.zhbj56.base.TabController;
import org.itheima.zhbj56.base.newscenter.InteractMenuController;
import org.itheima.zhbj56.base.newscenter.NewsMenuController;
import org.itheima.zhbj56.base.newscenter.PicMenuController;
import org.itheima.zhbj56.base.newscenter.TopicMenuController;
import org.itheima.zhbj56.bean.NewsCenterBean;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsCenterMenuBean;
import org.itheima.zhbj56.fragment.MenuFragment;
import org.itheima.zhbj56.utils.CacheUtils;
import org.itheima.zhbj56.utils.Constans;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.tab
 * @类名: NewsCenterTabController
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 上午8:48:47
 * @描述: 新闻中心对应的Controller
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class NewsCenterTabController extends TabController
{
	private static final long			DELAYED_TIME	= 2 * 60 * 1000;

	protected static final String		TAG				= "NewsCenterTabController";
	private FrameLayout					mContainer;									// 内容的容器
	private List<MenuController>		mMenuControllers;
	private List<NewsCenterMenuBean>	mMenuDatas;

	public NewsCenterTabController(Context context) {
		super(context);
	}

	@Override
	protected View initContentView(Context context)
	{
		// tv = new TextView(context);
		//
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		// tv.setTextColor(Color.RED);
		//
		// return tv;

		mContainer = new FrameLayout(mContext);
		return mContainer;
	}

	@Override
	public void initData()
	{
		// 设置menu按钮是否可见
		mIbMenu.setVisibility(View.VISIBLE);
		// 设置title
		mTvTitle.setText("新闻");

		final String url = Constans.NEWSCENTER_URL;
		// 到缓存中去取数据
		String json = CacheUtils.getString(mContext, url);
		long date = CacheUtils.getLong(mContext, url + "_date");
		if (!TextUtils.isEmpty(json))
		{
			Log.d(TAG, "读取到缓存数据");
			// 缓存中有数据
			// 到缓存中加载数据
			processData(json);

			if (System.currentTimeMillis() - date < DELAYED_TIME)
			{
				// 没有超出缓存的时间

				Log.d(TAG, "使用缓存，不使用网络");

				return;
			}
		}

		// 去网络加载数据
		// Url,请求方式,请求参数，消息头
		HttpUtils utils = new HttpUtils();

		// 异步的网络访问
		// 请求方式:
		// Url:
		// 请求参数，消息头
		// RequestParams params = new RequestParams();
		// params.addBodyParameter(name, value);//请求内容中的
		// params.addQueryStringParameter(name, value);//请求行中的
		// params.addHeader(name, value);// 请求消息头

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// int statusCode = responseInfo.statusCode;//状态码
				// 访问服务器成功
				// 访问接口是否成功

				// 状态码,内容
				String result = responseInfo.result;

				Log.d(TAG, "访问接口成功:" + result);

				Log.d(TAG, "设置缓存数据");
				// 设置缓存数据
				CacheUtils.setString(mContext, url, result);

				// 缓存时间
				CacheUtils.setLong(mContext, url + "_date", System.currentTimeMillis());

				// 数据的处理
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// 访问服务器不成功
				Log.d(TAG, "访问服务器失败:" + msg);

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
		// 1.json解析 String ----> Object
		Gson gson = new Gson();
		// Class指的是要转化成的类型,javabean的类型
		NewsCenterBean bean = gson.fromJson(json, NewsCenterBean.class);
		mMenuDatas = bean.data;

		// String title = bean.data.get(0).children.get(0).title;
		// // 校验
		// Log.d(TAG, "校验 ：" + title);

		// 2. Model ---> View
		// 2-1.给菜单加载数据
		MenuFragment menuFragment = ((MainUI) mContext).getMenuFragment();
		menuFragment.setData(mMenuDatas);

		// 2-2.给自己的内容实体加载数据
		mMenuControllers = new ArrayList<MenuController>();

		for (int i = 0; i < mMenuDatas.size(); i++)
		{
			NewsCenterMenuBean menuBean = mMenuDatas.get(i);
			int type = menuBean.type;

			switch (type)
			{
				case 1:
					mMenuControllers.add(new NewsMenuController(mContext, menuBean));// 新闻菜单
					break;
				case 10:
					mMenuControllers.add(new TopicMenuController(mContext));// 专题菜单
					break;
				case 2:
					mMenuControllers.add(new PicMenuController(mContext));// 组图菜单
					break;
				case 3:
					mMenuControllers.add(new InteractMenuController(mContext));// 互动菜单
					break;
				default:
					break;
			}
		}
		// 设置默认加载第一个
		switchMenu(0);
	}

	@Override
	public void switchMenu(int position)
	{
		// 清空容器
		mContainer.removeAllViews();

		MenuController menuController = mMenuControllers.get(position);

		// 加载视图
		View rootView = menuController.getRootView();
		mContainer.addView(rootView);

		// 设置title
		NewsCenterMenuBean bean = mMenuDatas.get(position);
		mTvTitle.setText(bean.title);

		// 加载数据
		menuController.initData();

	}
}
