package org.itheima.zhbj56.fragment;

import java.util.List;

import org.itheima.zhbj56.BaseFragment;
import org.itheima.zhbj56.R;
import org.itheima.zhbj56.bean.NewsCenterBean.NewsCenterMenuBean;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

	private ListView					mListView;
	private List<NewsCenterMenuBean>	mMenuDatas;	// 菜单对应的数据
	private int							mCurrentMenu;

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("菜单页面");
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// return tv;

		mListView = new ListView(mActivity);
		// 设置listView的样式
		mListView.setBackgroundColor(Color.BLACK);// 设置背景
		mListView.setPadding(0, 40, 0, 0);// 设置padding
		mListView.setCacheColorHint(android.R.color.transparent);// 设置为透明
		mListView.setSelector(android.R.color.transparent);

		return mListView;
	}

	/**
	 * 给菜单设置数据
	 * 
	 * @param datas
	 */
	public void setData(List<NewsCenterMenuBean> datas)
	{
		// 存储data
		this.mMenuDatas = datas;

		// 设置默认选中项
		mCurrentMenu = 0;

		// 给listView设置 adapter ---> List
		mListView.setAdapter(new MenuListAdapter());
	}

	class MenuListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mMenuDatas != null) { return mMenuDatas.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mMenuDatas != null) { return mMenuDatas.get(position); }
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = View.inflate(mActivity, R.layout.item_menu, null);
				holder = new ViewHolder();
				// 设置tag
				convertView.setTag(holder);

				// view的初始化
				holder.tv = (TextView) convertView;
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			NewsCenterMenuBean bean = mMenuDatas.get(position);

			// 设置数据
			holder.tv.setText(bean.title);

			// 判断是否是当前选中项
			// if (mCurrentMenu == position)
			// {
			// holder.tv.setEnabled(true);
			// }
			// else
			// {
			// holder.tv.setEnabled(false);
			// }

			holder.tv.setEnabled(mCurrentMenu == position);
			
			return convertView;
		}
	}

	class ViewHolder
	{
		TextView	tv;

	}
}
