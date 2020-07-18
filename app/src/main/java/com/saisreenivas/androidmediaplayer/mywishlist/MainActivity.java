package com.saisreenivas.androidmediaplayer.mywishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private Button saveButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(getApplicationContext());
        title = (EditText) findViewById(R.id.title_wish);
        content = (EditText) findViewById(R.id.body_wish);
        saveButton = (Button) findViewById(R.id.button_submit);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {

        MyWish wish = new MyWish();
        wish.setTitle(title.getText().toString().trim());
        wish.setContent(content.getText().toString().trim());
        dba.addWishes(wish);

        dba.close();

        title.setText("");
        content.setText("");

        Intent intent = new Intent(MainActivity.this, WishListActivity.class);
        startActivity(intent);

    }
}
