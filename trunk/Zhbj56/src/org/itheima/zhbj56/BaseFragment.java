package org.itheima.zhbj56;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56
 * @类名: BaseFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:18:47
 * @描述: fragment的基类
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public abstract class BaseFragment extends Fragment
{
	protected Activity	mActivity;	// 宿主activity

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// 加载数据
		initData();
	}

	protected abstract View initView();

	/**
	 * 如果孩子要加载数据，那么就复写此方法
	 */
	protected void initData()
	{

	}
}
