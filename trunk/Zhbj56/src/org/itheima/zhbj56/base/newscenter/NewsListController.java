package org.itheima.zhbj56.base.newscenter;

import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
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
public class NewsListController extends MenuController
{

	@ViewInject(R.id.news_list_pic_pager)
	private ViewPager		mPicPager;

	@ViewInject(R.id.news_list_tv_title)
	private TextView		mTvTitle;

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout	mPointContainer;

	public NewsListController(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View initView(Context context)
	{
		View view = View.inflate(mContext, R.layout.news_list_pager, null);

		// ViewUtils的注入
		ViewUtils.inject(this, view);

		return view;
	}

}
