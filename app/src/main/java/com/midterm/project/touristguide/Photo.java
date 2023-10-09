package com.midterm.project.touristguide;

import android.net.Uri;

public class Photo {
    private Uri ImageResource;

    public Photo(Uri imageResource) {
        ImageResource = imageResource;
    }

    public Uri getImageResource() {
        return ImageResource;
    }

    public void setImageResource(Uri imageResource) {
        ImageResource = imageResource;
    }
}
