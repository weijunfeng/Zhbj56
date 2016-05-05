package org.itheima.zhbj56.base.tab;

import org.itheima.zhbj56.base.TabController;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.tab
 * @类名: SettingTabController
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 上午8:51:21
 * @描述: 设置对应的Controller
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class SettingTabController extends TabController
{

	private TextView	tv;

	public SettingTabController(Context context) {
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
		// 设置menu按钮是否可见
		mIbMenu.setVisibility(View.GONE);
		// 设置title
		mTvTitle.setText("设置");

		// 设置内容数据
		tv.setText("设置的内容");
	}
}
