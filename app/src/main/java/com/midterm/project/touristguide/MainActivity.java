package com.midterm.project.touristguide;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView rcvLandmark;
    private LandmarkAdapter mLandmarkAdapter;
    private FloatingActionButton floatingActionButton;
    private List<Landmark> list;
    private RecyclerView rcvAddItem;
    Button btnSortAscending; // Initialize this button
    Button btnSortDescending; // Initialize this button
    ArrayList<Uri> uriArrayList = new ArrayList<>();
    private static final int REAR_PERMISSION = 101;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvLandmark = findViewById(R.id.rcv_landmark);

        mLandmarkAdapter = new LandmarkAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvLandmark.setLayoutManager(gridLayoutManager);

        mLandmarkAdapter.setData(getListLandmark());
        rcvLandmark.setAdapter(mLandmarkAdapter);

        // Initialize the buttons here
        btnSortAscending = findViewById(R.id.btnSortAscending);
        btnSortDescending = findViewById(R.id.btnSortDescending);

        btnSortAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort landmark items in ascending order
                mLandmarkAdapter.sortLandmarksAscending();
            }
        });

        btnSortDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort landmark items in descending order
                mLandmarkAdapter.sortLandmarksDescending();
            }
        });
    }




    public List<Landmark> getListLandmark() {
        List<Landmark> list = new ArrayList<>();
        list.add(new Landmark(new String[]{
                "android.resource://com.midterm.project.touristguide/drawable/dinh_doc_lap_1",
                "android.resource://com.midterm.project.touristguide/drawable/dinh_doc_lap_2",
                "android.resource://com.midterm.project.touristguide/drawable/dinh_doc_lap_3"
        }, "Independence Palace", "Vietnam Independence Palace: Iconic Saigon landmark, historic site where the Vietnam War ended, showcasing mid-century architecture and presidential history.", 5, "https://en.wikipedia.org/wiki/Independence_Palace", "02838223652", "135 Đ. Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh", false));
        list.add(new Landmark(new String[]{
                "android.resource://com.midterm.project.touristguide/drawable/cu_chi_1",
                "android.resource://com.midterm.project.touristguide/drawable/cu_chi_2",
                "android.resource://com.midterm.project.touristguide/drawable/cu_chi_3"
        }, "Cu Chi tunnels", "The tunnels of Củ Chi are an immense network of connecting tunnels located in the Củ Chi District of Ho Chi Minh City, Vietnam, and are part of a much larger network of tunnels that underlie much of the country.", 3, "https://en.wikipedia.org/wiki/C%E1%BB%A7_Chi_tunnels", "02837948830", "Phú Hiệp, Củ Chi, Ho Chi Minh City", false));
        list.add(new Landmark(new String[]{
                "android.resource://com.midterm.project.touristguide/drawable/benthanh_1",
                "android.resource://com.midterm.project.touristguide/drawable/benthanh_2"
        }, "Bến Thành Market", "Bến Thành Market (Vietnamese: Chợ Bến Thành) is located in the center of Hồ Chí Minh City, Vietnam in District 1. The market is one of the earliest surviving structures in Saigon and an important symbol of the city. Ben Thanh Market is a famous destination for many local and foreign tourists from all around the world", 4, "https://en.wikipedia.org/wiki/B%E1%BA%BFn_Th%C3%A0nh_Market", "0835210004", "Lê Lợi, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh\n", false));
        list.add(new Landmark(new String[]{
                "android.resource://com.midterm.project.touristguide/drawable/sg_post_office_1",
                "android.resource://com.midterm.project.touristguide/drawable/sg_post_office_2"
        }, "Sai Gon Central Post Office", "The building was constructed when Vietnam was part of French Indochina in the late 19th century. It counts with Gothic, Renaissance and French influences. It was constructed between 1886 and 1891 and is now a tourist attraction", 5, "https://en.wikipedia.org/wiki/Saigon_Central_Post_Office", "02839247247", "02 Công xã Paris, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh 70000", false));
        list.add(new Landmark(new String[]{
                "android.resource://com.midterm.project.touristguide/drawable/sg_opera_house_1",
                "android.resource://com.midterm.project.touristguide/drawable/sg_opera_house_2",
                "android.resource://com.midterm.project.touristguide/drawable/sg_opera_house_3"
        }, "Ho Chi Minh City Opera House", "The Municipal Theatre of Ho Chi Minh City, also known as Saigon Opera House, is an opera house in Ho Chi Minh City, Vietnam. It is an example of French Colonial architecture in Vietnam. Designed by French architects as the Opéra de Saïgon, the building was completed in 1900.", 4, "https://en.wikipedia.org/wiki/Municipal_Theatre_of_Ho_Chi_Minh_City", "02839247247", "07 Công Trường Lam Sơn, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh 710212", false));
        return list;



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favourite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favourite) {
            // Handle displaying the favorite list
            showFavoriteList();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void showFavoriteList() {
        List<Landmark> favoriteList = mLandmarkAdapter.getFavoriteList();

        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteList", new ArrayList<>(favoriteList)); // Convert to ArrayList for Serializable
        startActivity(intent);
    }

    public final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String ratingTemp =  adapterView.getItemAtPosition(i).toString();
        rating = Integer.parseInt(ratingTemp);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}