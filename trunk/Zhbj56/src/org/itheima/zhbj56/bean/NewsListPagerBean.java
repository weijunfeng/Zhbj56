package org.itheima.zhbj56.bean;

import java.util.List;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.bean
 * @类名: NewsListPagerBean
 * @创建者: 肖琦
 * @创建时间: 2015-4-25 上午10:44:40
 * @描述: 新闻list页面对应的数据
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class NewsListPagerBean
{
	public NewsListData	data;
	public int			retcode;

	public class NewsListData
	{
		public String					countcommenturl;
		public String					more;
		public List<NewsItemBean>		news;
		public String					title;
		public List<NewsTopicBean>		topic;
		public List<NewsTopNewsBean>	topnews;
	}

	public class NewsItemBean
	{
		public boolean	comment;
		public String	commentlist;
		public String	commenturl;
		public long		id;
		public String	listimage;
		public String	pubdate;
		public String	title;
		public String	type;
		public String	url;
	}

	public class NewsTopicBean
	{
		public String	description;
		public long		id;
		public String	listimage;
		public int		sort;
		public String	title;
		public String	url;
	}

	public class NewsTopNewsBean
	{
		public boolean	comment;
		public String	commentlist;
		public String	commenturl;
		public long		id;
		public String	pubdate;
		public String	title;
		public String	topimage;
		public String	type;
		public String	url;
	}
}
