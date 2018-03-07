package com.google.devrel.vrviewapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class TabFragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        View v =  inflater.inflate(R.layout.tab_fragment, container,false);
        panoWidgetView = (VrPanoramaView) v.findViewById(R.id.pano_view);
        roomSwitch = new roomSwitcher();
        panoWidgetView.setEventListener(roomSwitch);

        // Check to see if a recognition activity is present
        // if running on AVD virtual device it will give this message. The mic
        // required only works on an actual android device
        PackageManager pm = this.getActivity().getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            System.out.println("mic not working");
        }
        return v;
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
        String panoImageName = this.getArguments().getString("picture");

        // create the task passing the widget view and call execute to start.
        task = new ImageLoaderTask(panoWidgetView, viewOptions, panoImageName);
        task.execute(getActivity().getAssets());
        backgroundImageLoaderTask = task;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadPanoImage();
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
            System.out.println(matches);
            if (matches.contains("bedroom")) {
                startActivity(new Intent(this.getActivity(), BedroomActivity.class));
            } else if (matches.contains("kitchen")) {
                startActivity(new Intent(this.getActivity(), KitchenActivity.class));
            } else if (matches.contains("living")) {
                startActivity(new Intent(this.getActivity(), LivingRoomActivity.class));
            }
        }
    }
}
