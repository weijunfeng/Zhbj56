package org.itheima.zhbj56;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56
 * @类名: DetailUI
 * @创建者: 肖琦
 * @创建时间: 2015-4-26 下午2:46:08
 * @描述: 详情页面
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DetailUI extends Activity
{
	@ViewInject(R.id.detail_iv_back)
	private ImageView	mIvBack;

	@ViewInject(R.id.detail_iv_share)
	private ImageView	mIvShare;

	@ViewInject(R.id.detail_iv_textsize)
	private ImageView	mIvTextSize;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		// 初始化View
		initView();
	}

	private void initView()
	{
		// 注入
		ViewUtils.inject(this);
	}
}
