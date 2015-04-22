package org.itheima.zhbj56;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * 
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56
 * @类名: WelcomeUI
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 上午9:51:48
 * @描述: 欢迎界面
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class WelcomeUI extends Activity
{
	private final static long	ANIMATION_DURATION	= 1500;

	private View				mRootView;					// 根视图

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// // 去除title
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// // 去除状态栏
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.welcome);

		// 初始化View
		mRootView = findViewById(R.id.welcome_root);

		// 1. 旋转动画
		RotateAnimation rotateAnimation = new RotateAnimation(0, // 起始角度
																360,// 终止的角度
																Animation.RELATIVE_TO_SELF,
																0.5f,
																Animation.RELATIVE_TO_SELF,
																0.5f);
		rotateAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
		rotateAnimation.setFillAfter(true);

		// 2. 缩放动画(安装比例缩放)
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f,
															1f,
															0f,
															1f,
															Animation.RELATIVE_TO_SELF,
															0.5f,
															Animation.RELATIVE_TO_SELF,
															0.5f);
		scaleAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
		scaleAnimation.setFillAfter(true);

		// 3. 透明度的动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		alphaAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
		alphaAnimation.setFillAfter(true);

		// 动画集合
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(rotateAnimation);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);

		mRootView.startAnimation(set);
	}
}
