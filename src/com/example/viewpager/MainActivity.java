package com.example.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ViewPager viewPager;
	private TextView tv_intro;
	private LinearLayout dot_layout; //图片下面文字下的点
	private List<Ad> list = new ArrayList<Ad>();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			handler.sendEmptyMessageDelayed(0, 4000);//实现自动轮播图片
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		initViewPageListener();
		initDATA();
	}

	private void initViewPageListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				updateTvIntroAndDot();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {	
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});	
	}


	private void initDATA() {
		list.add(new Ad(R.drawable.a, "巩俐不低俗我就不低俗"));
		list.add(new Ad(R.drawable.b, "朴树归来,我们快点欢迎，朴树那些花儿开了，啦啦啦啦啦啦"));
		list.add(new Ad(R.drawable.c, "揭秘北京电影如何升级"));
		list.add(new Ad(R.drawable.d, "乐视网TV版大放送"));
		list.add(new Ad(R.drawable.e, "热血屌丝的反杀"));
		initDot();
		MyPageAdapter adapter = new MyPageAdapter();
		viewPager.setAdapter(adapter);
		int currentValue = Integer.MAX_VALUE / 2;
		int value = currentValue % list.size();
		viewPager.setCurrentItem(currentValue - value);//
		updateTvIntroAndDot();
		handler.sendEmptyMessageDelayed(0, 4000);
	}

	private void initDot() {
		for (int i = 0; i < list.size(); i++) {
			View view = new View(MainActivity.this);
			view.setBackgroundResource(R.drawable.dot_selector);
			LayoutParams layoutParams = new LayoutParams(8, 8);
			if (i!=0) {
				layoutParams.setMargins(5, 0, 0, 0);
			}
			view.setLayoutParams(layoutParams);
			dot_layout.addView(view);
		}
		
	}

	private void initUI() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tv_intro = (TextView) findViewById(R.id.tv_intro);
		dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
		
	}
	
	/**
	 * 更新图片下面的内容
	 */
	private void updateTvIntroAndDot() {
		int currentPage = viewPager.getCurrentItem() % list.size();
		tv_intro.setText(list.get(currentPage).getIntro());
		for (int i = 0; i < dot_layout.getChildCount(); i++) {
			dot_layout.getChildAt(i).setEnabled(i==currentPage);
		}
	}
	class MyPageAdapter extends PagerAdapter {

		/**
		 * 返回多少page
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;//将这个数尽量设置特别大，实现伪无线循环
		}

		/**
		 * true:表示不去创建，使用缓存，false:去重新创建
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		/**
		 * 类似于BaseAdapter的getView方法
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(MainActivity.this, R.layout.adapter_ad, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			imageView.setImageResource(list.get(position %  list.size()).getIconResId());//实现循环播放鲜果
			container.addView(view);//将数据填充到viewPager中，这个一定不能少
			return view;
		}

		/**
		 * 销毁page
		 * position 当前销毁的是第几个页面
		 * object 当前销毁的页面
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		
	}

}
