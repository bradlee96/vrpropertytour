package com.teambmw.vrpropertytour;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.util.ArrayList;

import static com.google.vr.sdk.widgets.common.VrWidgetView.DisplayMode.FULLSCREEN_STEREO;


public class RoomActivity extends AppCompatActivity {
    private VrPanoramaView panoWidgetView;
    private roomSwitcher roomSwitch;
    private ImageLoader backgroundImageLoader;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private class roomSwitcher extends VrPanoramaEventListener {
        @Override
        public void onClick() {
            voiceCommand();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment);

        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);
        panoWidgetView.setTransitionViewEnabled(false);
        panoWidgetView.setDisplayMode(FULLSCREEN_STEREO);
        roomSwitch = new roomSwitcher();
        panoWidgetView.setEventListener(roomSwitch);

        load360Image();
    }

    @Override
    public void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    public void onResume() {
        panoWidgetView.resumeRendering();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }

    private synchronized void load360Image() {
        ImageLoader task = backgroundImageLoader;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
        String panoImageName = this.getIntent().getExtras().getString("picture");
        task = new ImageLoader(panoWidgetView, viewOptions, panoImageName);
        task.execute(getAssets());
        backgroundImageLoader = task;
    }

    public void voiceCommand() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which room would you like to visit?");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RoomActivity.class);
            if (matches.contains("bedroom one")) {
                bundle.putString("picture", "bedroom1.jpg");
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            } else if (matches.contains("bedroom two")) {
                bundle.putString("picture", "bedroom2.jpg");
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            } else if (matches.contains("bedroom three")) {
                bundle.putString("picture", "kitchen.jpg"); // CHANGE HERE
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            } else if (matches.contains("kitchen")) {
                bundle.putString("picture", "kitchen.jpg");
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            } else if (matches.contains("sun room")) {
                bundle.putString("picture", "sunroom.jpg");
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            } else if (matches.contains("living room")) {
                bundle.putString("picture", "livingRoom.jpg");
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        }
    }
}
