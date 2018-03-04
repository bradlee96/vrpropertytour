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

        ImageButton btn = (ImageButton) findViewById(R.id.livingRoomButton);

        btn.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                startActivity(new Intent(FloorplanActivity.this, LivingRoomActivity.class));
            }
        });
    }
}
