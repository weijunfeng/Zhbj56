package org.itheima.zhbj56;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * 
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56
 * @类名: MainActivity
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 上午9:33:05
 * 
 * @描述: TODO
 */
public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
