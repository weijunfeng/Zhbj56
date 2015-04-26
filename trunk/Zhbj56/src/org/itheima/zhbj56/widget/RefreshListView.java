package org.itheima.zhbj56.widget;

import org.itheima.zhbj56.R;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.widget
 * @类名: RefreshListView
 * @创建者: 肖琦
 * @创建时间: 2015-4-25 下午4:18:53
 * @描述: 自定义的刷新的view
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class RefreshListView extends ListView
{
	private static final String	TAG						= "RefreshListView";

	private static final int	STATE_PULL_DOWN_REFRESH	= 0;						// 下拉刷新状态
	private static final int	STATE_RELEASE_REFRESH	= 1;						// 松开刷新状态
	private static final int	STATE_REFRESHING		= 2;						// 正在刷新状态

	private int					mCurrentState			= STATE_PULL_DOWN_REFRESH;	// 默认为下拉刷新状态

	private LinearLayout		mHeaderLayout;										// 头布局(刷新部分
																					// +
																					// 自定义部分)
	private View				mCustomHeaderView;									// 头布局中
																					// 自定义部分
	private View				mRefreshView;										// 头布局中刷新的部分

	private ImageView			mIvArrow;											// 刷新部分的箭头
	private ProgressBar			mProgressBar;										// 刷新部分的进度条
	private TextView			mTvState;											// 刷新部分的状态
	private TextView			mTvTime;											// 记录上次刷新的时间

	private int					mDownX;
	private int					mDownY;
	private int					mRefreshHeight;

	private RotateAnimation		down2UpAnimation;
	private RotateAnimation		up2DownAnimation;

	private int					mCurrentPaddingTop;

	public RefreshListView(Context context) {
		super(context);

		initHeaderLayout();
		initAnimation();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initHeaderLayout();
		initAnimation();
	}

	private void initAnimation()
	{
		// 由下往上的动画
		down2UpAnimation = new RotateAnimation(0, 180,
												Animation.RELATIVE_TO_SELF, 0.5f,
												Animation.RELATIVE_TO_SELF, 0.5f);
		down2UpAnimation.setDuration(300);
		down2UpAnimation.setFillAfter(true);

		up2DownAnimation = new RotateAnimation(-180, 0,
												Animation.RELATIVE_TO_SELF, 0.5f,
												Animation.RELATIVE_TO_SELF, 0.5f);
		up2DownAnimation.setDuration(300);
		up2DownAnimation.setFillAfter(true);
	}

	// 初始化头布局
	private void initHeaderLayout()
	{
		// 给ListView添加头布局
		// 加载头布局
		mHeaderLayout = (LinearLayout) View.inflate(getContext(), R.layout.refresh_header_layout, null);
		mRefreshView = mHeaderLayout.findViewById(R.id.refresh_header_refresh);
		mIvArrow = (ImageView) mHeaderLayout.findViewById(R.id.refresh_header_arrow);
		mProgressBar = (ProgressBar) mHeaderLayout.findViewById(R.id.refresh_header_progress);
		mTvState = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_state);
		mTvTime = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_time);

		// 添加到ListView的头布局中
		this.addHeaderView(mHeaderLayout);

		// 隐藏 刷新部分,设置头布局的paddingTop为刷新部分的高度的负数
		mRefreshView.measure(0, 0);
		mRefreshHeight = mRefreshView.getMeasuredHeight();
		Log.d(TAG, "刷新部分的高度:" + mRefreshHeight);
		mHeaderLayout.setPadding(0, -mRefreshHeight, 0, 0);
	}

	public void addCustomHeaderView(View headerView)
	{
		this.mCustomHeaderView = headerView;
		mHeaderLayout.addView(headerView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				mDownX = (int) (ev.getX() + 0.5f);
				mDownY = (int) (ev.getY() + 0.5f);
				break;
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) (ev.getX() + 0.5f);
				int moveY = (int) (ev.getY() + 0.5f);

				int diffX = moveX - mDownX;
				int diffY = moveY - mDownY;
				// 由上往下拉 diffY > 0

				// 如果当前的状态为正在刷新，就不去响应touch
				if (mCurrentState == STATE_REFRESHING)
				{
					break;
				}

				// 如果第一个View是可见的情况下，并且headerView完全可见的情况下
				if (mCustomHeaderView != null)
				{
					// 如果CustomHeaderView没有完全露出来，不去响应下拉刷新

					// 取出listView的左上角的点
					int[] lliw = new int[2];
					this.getLocationInWindow(lliw);
					Log.d(TAG, "listView Y : " + lliw[1]);

					// 取出customheaderView左上角的点
					int[] hliw = new int[2];
					mCustomHeaderView.getLocationInWindow(hliw);
					Log.d(TAG, "customHeader Y : " + hliw[1]);

					if (hliw[1] < lliw[1])
					{
						// 不响应下拉刷新
						break;
					}
				}

				// 如果第一个View是可见的情况下
				if (getFirstVisiblePosition() == 0)
				{
					if (diffY > 0)
					{
						Log.d(TAG, "第一个View可见");
						// 希望看到刷新的View
						// 改变头布局的PaddingTop
						mCurrentPaddingTop = diffY - mRefreshHeight;
						mHeaderLayout.setPadding(0, mCurrentPaddingTop, 0, 0);

						// 如果paddingTop是负数值的时候，说明刷新部分没有完全露出来，现在的状态为 下拉刷新
						if (mCurrentPaddingTop < 0 && mCurrentState != STATE_PULL_DOWN_REFRESH)
						{
							// 说明刷新部分没有完全露出来，现在的状态为 下拉刷新
							mCurrentState = STATE_PULL_DOWN_REFRESH;
							Log.d(TAG, "当前状态为 : 下拉刷新");
							// UI需要刷新
							refreshUI();
						}
						else if (mCurrentPaddingTop >= 0 && mCurrentState != STATE_RELEASE_REFRESH)
						{
							// 如果刷新部分完全露出来说明PaddingTop>=0,现在的状态为 释放刷新
							mCurrentState = STATE_RELEASE_REFRESH;
							Log.d(TAG, "当前状态为 : 释放刷新");
							// UI需要刷新
							refreshUI();
						}
						// 消费掉
						return true;
					}
				}

				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mDownX = 0;// 清空数据
				mDownY = 0;// 清空数据

				// 松开时的逻辑

				// 如果现在是 松开刷新的状态
				if (mCurrentState == STATE_RELEASE_REFRESH)
				{
					Log.d(TAG, "up后，正在刷新");
					// 1. paddingTop应该到 0
					// mHeaderLayout.setPadding(0, 0, 0, 0);//太突然

					// ObjectAnimator.ofInt(mHeaderLayout, "scrollX", 0, 1,2,1);
					int start = mCurrentPaddingTop;
					int end = 0;
					doHeaderAnimation(start, end);

					// 2. 刷新的状态应该变为 正在刷新
					mCurrentState = STATE_REFRESHING;
					// 3. UI刷新
					refreshUI();
				}

				// 如果 现在的状态是 下拉刷新
				if (mCurrentState == STATE_PULL_DOWN_REFRESH)
				{
					Log.d(TAG, "up后，下拉刷新");

					// 1. paddingTop = -refreshHeight
					// mHeaderLayout.setPadding(0, -mRefreshHeight, 0, 0);// 太突然

					int start = mCurrentPaddingTop;
					int end = -mRefreshHeight;
					doHeaderAnimation(start, end);

				}

				break;
			default:
				break;
		}

		return super.onTouchEvent(ev);
	}

	private void doHeaderAnimation(int start, int end)
	{
		// 模拟数据的变化 100-->0 100,90,80
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(300);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				int animatedValue = (Integer) animation.getAnimatedValue();

				mHeaderLayout.setPadding(0, animatedValue, 0, 0);
			}
		});
		animator.start();
	}

	// 更新UI
	private void refreshUI()
	{
		switch (mCurrentState)
		{
			case STATE_PULL_DOWN_REFRESH:
				// 下拉刷新
				// 1.箭头显示，进度不显示
				mIvArrow.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);
				// 2.箭头由上往下 :动画操作
				mIvArrow.startAnimation(up2DownAnimation);
				// 3.文本变为 下拉刷新
				mTvState.setText("下拉刷新");
				break;
			case STATE_RELEASE_REFRESH:
				// 释放刷新
				// 1.箭头显示，进度不显示
				mIvArrow.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);

				// 2.箭头由下往上 :动画操作
				mIvArrow.startAnimation(down2UpAnimation);

				// 3.文本变为 松开刷新
				mTvState.setText("松开刷新");

				break;
			case STATE_REFRESHING:
				// 正在刷新
				// 清空动画
				mIvArrow.clearAnimation();
				// 1.箭头不显示，进度显示
				mIvArrow.setVisibility(View.INVISIBLE);
				mProgressBar.setVisibility(View.VISIBLE);

				// 2.文本变为 正在刷新
				mTvState.setText("正在刷新");
				break;

			default:
				break;
		}
	}
}
