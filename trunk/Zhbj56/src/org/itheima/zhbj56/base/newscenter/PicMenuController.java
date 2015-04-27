package org.itheima.zhbj56.base.newscenter;

import org.itheima.zhbj56.R;
import org.itheima.zhbj56.base.MenuController;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
public class PicMenuController extends MenuController
{

	// private TextView tv;

	@ViewInject(R.id.newscenter_pic_list_view)
	private ListView	mListView;

	@ViewInject(R.id.newscenter_pic_grid_view)
	private GridView	mGridView;

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

		return view;
	}

	@Override
	public void initData()
	{
		// 通过网络获取list数据

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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			return null;
		}

	}

}
