package org.itheima.zhbj56.fragment;

import org.itheima.zhbj56.BaseFragment;
import org.itheima.zhbj56.R;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.fragment
 * @类名: ContentFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:17:16
 * @描述: 主页的内容
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class ContentFragment extends BaseFragment
{
	@ViewInject(R.id.content_pager)
	private ViewPager	mPager; // ViewPager

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("主页面");
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// return tv;

		View view = View.inflate(mActivity, R.layout.content, null);

		// 注入ViewUtils工具
		ViewUtils.inject(this, view);

		return view;
	}
	
	

}
