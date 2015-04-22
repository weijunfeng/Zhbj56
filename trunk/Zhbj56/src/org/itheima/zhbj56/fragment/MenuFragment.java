package org.itheima.zhbj56.fragment;

import org.itheima.zhbj56.BaseFragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.fragment
 * @类名: MenuFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:17:57
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class MenuFragment extends BaseFragment
{

	@Override
	protected View initView()
	{
		TextView tv = new TextView(mActivity);

		tv.setText("菜单页面");
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
	}
}
