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
	private LinearLayout dot_layout; //ͼƬ���������µĵ�
	private List<Ad> list = new ArrayList<Ad>();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			handler.sendEmptyMessageDelayed(0, 4000);//ʵ���Զ��ֲ�ͼƬ
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
		list.add(new Ad(R.drawable.a, "�����������ҾͲ�����"));
		list.add(new Ad(R.drawable.b, "��������,���ǿ�㻶ӭ��������Щ�������ˣ�������������"));
		list.add(new Ad(R.drawable.c, "���ر�����Ӱ�������"));
		list.add(new Ad(R.drawable.d, "������TV������"));
		list.add(new Ad(R.drawable.e, "��Ѫ��˿�ķ�ɱ"));
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
	 * ����ͼƬ���������
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
		 * ���ض���page
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;//����������������ر��ʵ��α����ѭ��
		}

		/**
		 * true:��ʾ��ȥ������ʹ�û��棬false:ȥ���´���
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		/**
		 * ������BaseAdapter��getView����
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(MainActivity.this, R.layout.adapter_ad, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			imageView.setImageResource(list.get(position %  list.size()).getIconResId());//ʵ��ѭ�������ʹ�
			container.addView(view);//��������䵽viewPager�У����һ��������
			return view;
		}

		/**
		 * ����page
		 * position ��ǰ���ٵ��ǵڼ���ҳ��
		 * object ��ǰ���ٵ�ҳ��
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		
	}

}
