package com.cheesesquare;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @author zhengjingle
 */
public class MainActivity extends Activity {

    ImageView iv_openDrawer;
    String[] titles={"CATEGORY1","CATEGORY2","CATEGORY3"};
    TabLayout tabLayout;
    List<View> itemList;
    ViewPager viewpager;

    DrawerLayout drawer_layout;
    LinearLayout ll_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        iv_openDrawer=(ImageView)findViewById(R.id.imageView1);
        iv_openDrawer.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                
                drawer_layout.openDrawer(Gravity.LEFT);
            }

        });
        
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        
        viewpager=(ViewPager)findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyPagerAdapter());
        tabLayout.setupWithViewPager(viewpager);
        
        drawer_layout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ll_list=(LinearLayout)findViewById(R.id.ll_list);
        ll_list.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                
                drawer_layout.closeDrawers();
            }

        });
    }

    private void startDetail(){
        startActivity(new Intent(this,DetailActivity.class));
    }

    private void initData(){
        itemList=new LinkedList<View>();
        for(int i=0;i<titles.length;i++){
            ListView listView=new ListView(this);
            listView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            listView.setAdapter(new MyAdapter());
            listView.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    
                    startDetail();
                }

            });
            itemList.add(listView);
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            
            return 50;
        }

        @Override
        public Object getItem(int position) {
            
            return null;
        }

        @Override
        public long getItemId(int position) {
            
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            ViewHolder viewHolder=null;
            if(convertView==null){
                convertView=View.inflate(MainActivity.this, R.layout.item, null);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.tv_cheese.setText("cheese-"+position);
            return convertView;
        }

        class ViewHolder{
            TextView tv_cheese;
            public ViewHolder(View view){
                tv_cheese=(TextView)view.findViewById(R.id.textView1);
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(itemList.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(itemList.get(arg1));
            return itemList.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
