package org.itheima.zhbj56.base.tab;

import org.itheima.zhbj56.base.TabController;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.tab
 * @类名: HomeTabController
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午4:43:41
 * @描述: 首页对应的controller
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class HomeTabController extends TabController
{

	private TextView	tv;

	public HomeTabController(Context context) {
		super(context);
	}

	@Override
	protected View initContentView(Context context)
	{
		tv = new TextView(context);

		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.RED);

		return tv;
	}

	@Override
	public void initData()
	{
		tv.setText("首页的内容");
	}
}
