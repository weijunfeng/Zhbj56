package org.itheima.zhbj56;

import org.itheima.zhbj56.utils.CacheUtils;
import org.itheima.zhbj56.utils.ShareUtils;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.TextSize;
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
	public static final String		KEY_URL			= "url";		// url对应的key

	protected static final String	TAG				= "DetailUI";

	private static final String		KEY_TEXT_SIZE	= "text_size";	// 设置文本大小

	@ViewInject(R.id.detail_iv_back)
	private ImageView				mIvBack;

	@ViewInject(R.id.detail_iv_share)
	private ImageView				mIvShare;

	@ViewInject(R.id.detail_iv_textsize)
	private ImageView				mIvTextSize;

	@ViewInject(R.id.detail_wv)
	private WebView					mWebView;

	private int						mSelectedItem	= 2;			// 默认正常字体

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去掉title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.detail);

		// 初始化View
		initView();

		// 加载数据
		initData();
	}

	private void initView()
	{
		// 注入
		ViewUtils.inject(this);
	}

	private void initData()
	{
		// 取缓存
		mSelectedItem = (int) CacheUtils.getLong(this, KEY_TEXT_SIZE, mSelectedItem);
		setTextSize();

		String url = getIntent().getStringExtra(KEY_URL);

		// 监听
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				Log.d(TAG, "页面开始加载");
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				Log.d(TAG, "页面结束加载");
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				Log.d(TAG, "进度 ：" + newProgress);
			}
		});

		// 页面显示
		mWebView.loadUrl(url);
		// mWebView.loadUrl("http://www.itheima.com");
		// WebView的设置

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true); // 设置js可见
		settings.setBuiltInZoomControls(true);// 设置放大缩小的控件
		settings.setUseWideViewPort(true);// 双击缩放
	}

	@OnClick(R.id.detail_iv_back)
	public void clickBack(View view)
	{
		finish();
	}

	@OnClick(R.id.detail_iv_share)
	public void clickShare(View view)
	{
		// 分享
		ShareUtils.showShare(this);
	}

	@OnClick(R.id.detail_iv_textsize)
	public void clickTextSizeChange(View view)
	{
		// 显示dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("设置字体");// 设置title

		CharSequence[] items = new CharSequence[] {
				"超大号字体",
				"大号字体",
				"正常字体",
				"小号字体",
				"超小号字体"
		};

		builder.setSingleChoiceItems(items, mSelectedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// 点击单个item
				mSelectedItem = which;
			}
		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// 设置数据缓存
				CacheUtils.setLong(DetailUI.this, KEY_TEXT_SIZE, mSelectedItem);

				// 设置字体的大小
				setTextSize();
			}
		});

		// 显示
		builder.show();
	}

	protected void setTextSize()
	{
		TextSize size = null;

		switch (mSelectedItem)
		{
			case 0:
				size = TextSize.LARGEST;
				break;
			case 1:
				size = TextSize.LARGER;
				break;
			case 2:
				size = TextSize.NORMAL;
				break;
			case 3:
				size = TextSize.SMALLER;
				break;
			case 4:
				size = TextSize.SMALLEST;
				break;
			default:
				break;
		}

		mWebView.getSettings().setTextSize(size);
	}
}
