package org.itheima.zhbj56.bean;

import java.util.List;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.bean
 * @类名: NewsCenterBean
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 上午10:40:32
 * @描述: 新闻中心页面对应的数据
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class NewsCenterBean
{
	public List<NewsCenterMenuBean>	data;
	public List<Long>				extend;
	public int						retcode;

	public class NewsCenterMenuBean
	{
		public List<NewsBean>	children;
		public long				id;
		public String			title;
		public int				type;

		public String			url;
		public String			url1;

		public String			dayurl;
		public String			excurl;
		public String			weekurl;
	}

	public class NewsBean
	{
		public long		id;
		public String	title;
		public int		type;
		public String	url;
	}
}
