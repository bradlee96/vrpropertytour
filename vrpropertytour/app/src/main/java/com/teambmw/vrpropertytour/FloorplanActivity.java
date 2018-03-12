package com.teambmw.vrpropertytour;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;


public class FloorplanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorplan);

        ImageButton livingRoomBtn = (ImageButton) findViewById(R.id.livingRoomButton);
        ImageButton bedroom1Btn = (ImageButton) findViewById(R.id.bedroom1Button);
        ImageButton bedroom2Btn = (ImageButton) findViewById(R.id.bedroom2Button);
        ImageButton bedroom3Btn = (ImageButton) findViewById(R.id.bedroom3Button);
        ImageButton sunRoomBtn = (ImageButton) findViewById(R.id.sunRoomButton);
        ImageButton kitchenBtn = (ImageButton) findViewById(R.id.kitchenButton);

        livingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "livingRoom.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bedroom1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "bedroom1.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bedroom2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "bedroom2.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bedroom3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "bedroom3.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        sunRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "sunroom.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        kitchenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "kitchen.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
