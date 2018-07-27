package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static LeftAdapter myleftAdapter = null;
    static ArrayAdapter<String> l;
    static int num=0;
    static List<Goods> goodsList=new ArrayList<Goods>();
    static ListView listViewleft;
    static StickyListHeadersListView listViewright;
    static String[]listItems=new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.orange_color));
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        listItems[0]="蔬菜";
        listItems[1]="肉类";
        listItems[2]="水果";
        listItems[3]="佐料";
        listItems[4]="工具";
     /*   l=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);*/
       myleftAdapter=new LeftAdapter(this,listItems);
    }
    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_message) {
            Toast.makeText(MainActivity.this,"留言模块",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView=null ;
            final int position=getArguments().getInt(ARG_SECTION_NUMBER);
            List<Goods> goodsList=new ArrayList<>();

            if(position==1){
                RightAdapter rightadapter;

                rootView = inflater.inflate(R.layout.fragment_home, container, false);
                final StickyListHeadersListView stickyListHeadersListView = (StickyListHeadersListView) rootView.findViewById(R.id.right);
                listViewleft=(ListView)rootView.findViewById(R.id.left);
                listViewleft.setAdapter(myleftAdapter);
                //左侧点击事件监听 z
                listViewleft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        myleftAdapter.changeSelected(position);
                        stickyListHeadersListView.setSelection(position*10);
                    }
                });
                //初始化

               //模拟初始化数据

                 for(int i=0;i<5;i++){
                     for(int j=0;j<10;j++){
                         Goods goods=new Goods();
                         goods.setHeadId(i);
                         goods.setHeadName(listItems[i]);
                         goods.setName(j+"");
                         goods.setImageId(R.mipmap.ic_launcher);
                         goodsList.add(goods);
                     }
                 }
                rightadapter=new RightAdapter(getActivity(),goodsList);


         /*       stickyListHeadersListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                        myleftAdapter.changeSelected((int)headerId);
                        Log.e("点击的标题",headerId+"");
                    }
                });*/
                stickyListHeadersListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                        myleftAdapter.changeSelected((int)headerId);
                        Log.e("点击的标题",headerId+"");
                    }
                });
                stickyListHeadersListView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener() {
                    @Override
                    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
                        myleftAdapter.changeSelected((int)headerId);
                        Log.e("滑动的标题",headerId+"");
                    }
                });
              //设置内容的点击事件
                stickyListHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity(), "i:" + i, Toast.LENGTH_SHORT).show();
                    }
                });



                stickyListHeadersListView.setAdapter(rightadapter);

            }
            if(position==2){
                rootView = inflater.inflate(R.layout.fragment_order, container, false);

            }
            return rootView;
        }
    }
    public class LeftAdapter extends BaseAdapter {
        private Context context; //运行上下文
        private String[]listItems; //商品信息集合
        private LayoutInflater listContainerLeft;
        private int mSelect = 0;   //选中项
        public LeftAdapter(Context context,String[]listItems){
            this.context=context;
            this.listItems=listItems;
        }
        @Override
        public int getCount() {
            return listItems.length;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            listContainerLeft = LayoutInflater.from(context);
            ListItemView listItemView=null;
              if(convertView==null){
                  listItemView=new ListItemView();
                  convertView = listContainerLeft.inflate(R.layout.left_listview, null);
                  listItemView.tv_title=(TextView)convertView.findViewById(R.id.title);
                  listItemView.imageView=(ImageView)convertView.findViewById(R.id.imageView);
                  convertView.setTag(listItemView);
              }
              else{
                  listItemView = (ListItemView)convertView.getTag();
              }
              if(mSelect==position){
                convertView.setBackgroundResource(R.color.md_grey_50);  //选中项背景
                  listItemView.imageView.setVisibility(View.VISIBLE);
              }else{
                convertView.setBackgroundResource(R.color.md_grey_100);  //其他项背景
                  listItemView.imageView.setVisibility(View.GONE);
              }
            listItemView.tv_title.setText(listItems[position]);
            return convertView;
        }

        public void changeSelected(int positon){ //刷新方法
                mSelect = positon;
                notifyDataSetChanged();
        }
        class ListItemView {
            TextView tv_title;
            ImageView imageView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "采购";
                case 1:
                    return "订单";
            }
            return null;
        }
    }
}
