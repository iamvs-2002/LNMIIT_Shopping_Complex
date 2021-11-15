package com.example.lnmiitshoppingcomplex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
class CategoryItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] name;
    private int[] imgurl;
    private int[] price;
    private ImageView img;
    private TextView nametv,pricetv;
    public CategoryItemAdapter(Context c, String[] name,int[] imgurl, int[] price){
        context = c;
        this.name = name;
        this.imgurl = imgurl;
        this.price = price;
    }
    @Override
    public int getCount() {
        return name.length;
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
        if (layoutInflater==null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.category_frame_item, null);
        }
        img = convertView.findViewById(R.id.citem_img);
        nametv = convertView.findViewById(R.id.citem_name);
        pricetv = convertView.findViewById(R.id.citem_price);
        img.setImageResource(imgurl[position]);
        nametv.setText(name[position]);
        pricetv.setText(String.valueOf(price[position]));
        return convertView;
    }
}