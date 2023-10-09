package com.midterm.project.touristguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class LandmarkDetail extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_SEND = 1;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Photo> mListPhoto;
    private Landmark landmark;
    private String name,des,phone,location,wiki;
    private ArrayList<Parcelable> uris;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPager2.getCurrentItem();
            if(currentPosition == mListPhoto.size() -1 ){
                viewPager2.setCurrentItem(0);
            }
            else {
                viewPager2.setCurrentItem(currentPosition + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_detail);
        Bundle bundle = getIntent().getExtras();
        viewPager2 = findViewById(R.id.video_pager_2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);
        TextView detailName = findViewById(R.id.detail_Name);
        TextView detailDes = findViewById(R.id.detail_description);
        TextView detailPhone = findViewById(R.id.detail_phone);
        TextView detailAddress = findViewById(R.id.detail_address);
        if(bundle == null){
            return;
        }

        uris = bundle.getParcelableArrayList("imagesUri");
        name = bundle.getString("detailName");
        des = bundle.getString("detailDescription");
        phone = bundle.getString("detailPhone");
        location = bundle.getString("detailLocation");
        wiki = bundle.getString("detailWiki");


        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);


        detailName.setText(name);
        detailDes.setText(des);
        detailPhone.setText("Phone Number: "+phone);
        detailAddress.setText("Address: "+ location);
        mListPhoto = getListPhoto();
        PhotoAdapter photoAdapter = new PhotoAdapter(mListPhoto);
        viewPager2.setAdapter(photoAdapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        for (Parcelable p : uris) {
            list.add(new Photo((Uri) p));
        }
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }

    public void makePhoneCallButton(View view) {
        makePhoneCall();
    }
    public void makePhoneCall(){
        if(phone.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(LandmarkDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LandmarkDetail.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else {
                String dial = "tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else {
            Toast.makeText(LandmarkDetail.this,"Phone number not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void openInMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+location));
        startActivity(intent);
    }

    public void openInBrowser(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(wiki));
        startActivity(intent);
    }

    public void makeMessage(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:" + phone));
        startActivity(intent);
    }


}