package com.example.admin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by admin on 2017/3/7.
 */

public class RightAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context context;
/*    private List<String> headList;
    private List<String> bodyList;*/
    List<Goods> goodsList;

    public RightAdapter(Context context,List<Goods> goodsList) {
        this.context = context;
        this.goodsList=goodsList;
    }
/*    public void setHeadList(List<String> headList) {
        this.headList = headList;
        notifyDataSetChanged();
    }

    public void setBodyList(List<String> bodyList) {
        this.bodyList = bodyList;
        notifyDataSetChanged();
    }*/

    //设置数据的个数
    @Override
    public int getCount() {
        return goodsList.size();
    }

    //设置item的条数
    @Override
    public Object getItem(int i) {
        return goodsList.get(i);
    }

    //获得相应数据集合中特定位置的数据项
    @Override
    public long getItemId(int i) {
        return i;
    }

    //获得头部相应数据集合中特定位置的数据项
    @Override
    public long getHeaderId(int position) {
        return goodsList.get(position).getHeadId();
    }

    //绑定内容的数据
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        RightAdapter.BodyHolder bodyHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_body, viewGroup, false);
            bodyHolder = new RightAdapter.BodyHolder(view);
            view.setTag(bodyHolder);
        } else {
            bodyHolder = (RightAdapter.BodyHolder) view.getTag();
        }
        //设置数据
        bodyHolder.bodyTv.setText(goodsList.get(i).getName());
        bodyHolder.bodyIm.setImageResource(goodsList.get(i).getImageId());
        return view;
    }

    //绑定头部的数据
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        RightAdapter.HeadHolder headHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_head, parent, false);
            headHolder = new RightAdapter.HeadHolder(convertView);
            convertView.setTag(headHolder);
        } else {
            headHolder = (RightAdapter.HeadHolder) convertView.getTag();
        }
        //设置数据
        headHolder.headTv.setText(goodsList.get(position).getHeadName());
        return convertView;
    }


    //头部的内部类
    class HeadHolder {
        private TextView headTv;

        public HeadHolder(View itemHeadView) {

            headTv = (TextView) itemHeadView.findViewById(R.id.item_head_tv);
        }
    }

    //内容的内部类
    class BodyHolder {
        private TextView bodyTv;
        private ImageView bodyIm;
        public BodyHolder(View itemBodyView) {

            bodyTv = (TextView) itemBodyView.findViewById(R.id.item_body_tv1);
            bodyIm=(ImageView)itemBodyView.findViewById(R.id.item_body_image);
        }
    }
}
