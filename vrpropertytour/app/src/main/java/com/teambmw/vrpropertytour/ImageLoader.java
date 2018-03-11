package com.teambmw.vrpropertytour;

import android.os.AsyncTask;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import java.lang.ref.WeakReference;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader extends AsyncTask<AssetManager, Void, Bitmap> {
    private final String assetName;
    private final WeakReference<VrPanoramaView> viewReference;
    private final VrPanoramaView.Options viewOptions;

    private static WeakReference<Bitmap> lastBitmap = new WeakReference<>(null);
    private static String lastName;

    public ImageLoader(VrPanoramaView view, VrPanoramaView.Options viewOptions, String assetName) {
        viewReference = new WeakReference<>(view);
        this.viewOptions = viewOptions;
        this.assetName = assetName;
    }

    @Override
    protected Bitmap doInBackground(AssetManager... params) {
        AssetManager assetManager = params[0];

        if (assetName.equals(lastName) && lastBitmap.get() != null) {
            return lastBitmap.get();
        }

        try(InputStream istr = assetManager.open(assetName)) {
            Bitmap b = BitmapFactory.decodeStream(istr);
            lastBitmap = new WeakReference<>(b);
            lastName = assetName;
            return b;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        final VrPanoramaView vw = viewReference.get();
        if (vw != null && bitmap != null) {
            vw.loadImageFromBitmap(bitmap, viewOptions);
        }
    }
}
