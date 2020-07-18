package com.saisreenivas.androidmediaplayer.mywishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

public class WishListActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ListView listView;
    private ArrayList<MyWish> wishList = new ArrayList<>();
    private WishAdapter wishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        listView = (ListView) findViewById(R.id.list);
        refreshData();



    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(WishListActivity.this, MainActivity.class));
        finish();
    }

    private void refreshData() {
        wishList.clear();

        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<MyWish> allWishes = dba.getWishes();

        for(int i=0; i< allWishes.size(); i++){

            String title = allWishes.get(i).getTitle();
            String content = allWishes.get(i).getContent();
            String dateText = allWishes.get(i).getRecordDate();

            MyWish wish = new MyWish();
            wish.setTitle(title);
            wish.setContent(content);
            wish.setRecordDate(dateText);
            wish.set_id(allWishes.get(i).get_id());

            wishList.add(wish);

        }
        dba.close();

        //setup Adapter
        wishAdapter = new WishAdapter(WishListActivity.this, R.layout.each_view, wishList);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();


    }

    private class WishAdapter extends ArrayAdapter<MyWish>{

        Activity activity;
        int layoutresource;
        MyWish wish;
        ArrayList<MyWish> mData = new ArrayList<>();

        public WishAdapter(Activity cxt, int resource, ArrayList<MyWish> data) {
            super(cxt, resource, data);
            activity = cxt;
            layoutresource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public MyWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View viewRow = convertView;
            ViewHolder holder = null;
            if (viewRow == null || (viewRow.getTag() == null)) {

                LayoutInflater inflater = LayoutInflater.from(activity);
                viewRow = inflater.inflate(layoutresource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) viewRow.findViewById(R.id.list_title);
                holder.mDate = (TextView) viewRow.findViewById(R.id.list_date);
                holder.mRelative = (RelativeLayout) viewRow.findViewById(R.id.relative);

                viewRow.setTag(holder);

            }else {
                    holder = (ViewHolder) viewRow.getTag();
            }

            holder.myWish = getItem(position);
            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getRecordDate());

            final ViewHolder finalHolder = holder;
            holder.mRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = finalHolder.myWish.getContent().toString();
                    String dateText = finalHolder.myWish.getRecordDate().toString();
                    String title = finalHolder.myWish.getTitle().toString();

                    Intent intent = new Intent(WishListActivity.this, EachListItem.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", text);
                    intent.putExtra("recordDate", dateText);
                    intent.putExtra("id", finalHolder.myWish.get_id());

                    startActivity(intent);

                }
            });

            return viewRow;
        }

        class ViewHolder{

            MyWish myWish;
            TextView mTitle;
            int mId;
            RelativeLayout mRelative;
            TextView mContent;
            TextView mDate;
        }
    }
}
