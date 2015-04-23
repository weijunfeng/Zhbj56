package org.itheima.zhbj56.base.newscenter;

import org.itheima.zhbj56.base.MenuController;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
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

	private TextView	tv;

	public NewsMenuController(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View initView(Context context)
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
		// 设置实体数据
		tv.setText("新闻菜单对应的页面");
	}

}
