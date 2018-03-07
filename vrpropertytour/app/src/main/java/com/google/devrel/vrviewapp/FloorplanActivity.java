package com.google.devrel.vrviewapp;

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
        ImageButton bedroomBtn = (ImageButton) findViewById(R.id.bedroomButton);
        ImageButton kitchenBtn = (ImageButton) findViewById(R.id.kitchenButton);

        livingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "livingroomSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bedroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "bedroomSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        kitchenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FloorplanActivity.this, RoomActivity.class);
                bundle.putString("picture", "kitchenSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
