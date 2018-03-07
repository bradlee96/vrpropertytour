package com.google.devrel.vrviewapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.vr.sdk.widgets.common.VrWidgetView.DisplayMode.FULLSCREEN_STEREO;


public class RoomActivity extends AppCompatActivity {
    private VrPanoramaView panoWidgetView;
    private roomSwitcher roomSwitch;
    private ImageLoaderTask backgroundImageLoaderTask;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private class roomSwitcher extends VrPanoramaEventListener {
        @Override
        public void onClick() {
            System.out.println("registered event");
            startVoiceRecognitionActivity();
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

        // Check to see if a recognition activity is present
        // if running on AVD virtual device it will give this message. The mic
        // required only works on an actual android device
        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            System.out.println("mic not working");
        }
        loadPanoImage();
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

    private synchronized void loadPanoImage() {
        ImageLoaderTask task = backgroundImageLoaderTask;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }

        // pass in the name of the image to load from assets.
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;

        // use the name of the image in the assets/ directory.
        String panoImageName = this.getIntent().getExtras().getString("picture");

        // create the task passing the widget view and call execute to start.
        task = new ImageLoaderTask(panoWidgetView, viewOptions, panoImageName);
        task.execute(getAssets());
        backgroundImageLoaderTask = task;
    }

    public void startVoiceRecognitionActivity() {
        System.out.println("Voice recognition started");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RoomActivity.class);
            if (matches.contains("bedroom")) {
                bundle.putString("picture", "bedroomSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (matches.contains("kitchen")) {
                bundle.putString("picture", "kitchenSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (matches.contains("living")) {
                bundle.putString("picture", "livingroomSmall.jpg");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
