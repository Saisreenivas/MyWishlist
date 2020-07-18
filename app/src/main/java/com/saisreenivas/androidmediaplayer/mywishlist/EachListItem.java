package com.saisreenivas.androidmediaplayer.mywishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import data.DatabaseHandler;

public class EachListItem extends AppCompatActivity {
    private TextView title, content, date;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_list_item);

        title = (TextView) findViewById(R.id.title_each_item);
        content = (TextView) findViewById(R.id.body_each_item);
        date = (TextView) findViewById(R.id.date_each_item);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        final Bundle extras = getIntent().getExtras();
        if(extras != null){

            title.setText(extras.getString("title"));
            content.setText(extras.getString("content"));
            date.setText("Created: " + extras.getString("recordDate"));

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteWishes(extras.getInt("id"));

                    startActivity(new Intent(EachListItem.this, WishListActivity.class));
                }
            });
        }
    }
}
