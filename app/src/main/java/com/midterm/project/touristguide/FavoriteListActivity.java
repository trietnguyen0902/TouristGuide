package com.midterm.project.touristguide;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        // Get the favorite list from the intent
        List<Landmark> favoriteList =
                (List<Landmark>) getIntent().getSerializableExtra("favoriteList");

        if (favoriteList != null && !favoriteList.isEmpty()) {
            // Create a list of favorite landmark names
            List<String> favoriteLandmarkNames = new ArrayList<>();
            for (Landmark landmark : favoriteList) {
                favoriteLandmarkNames.add(landmark.getName());
            }

            // Display the favorite landmark names in a ListView
            ListView listView = findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, favoriteLandmarkNames);
            listView.setAdapter(adapter);
        } else {
            // Handle the case where favoriteList is null or empty (e.g., display a message)
            Toast.makeText(this, "Your favorite list is empty.", Toast.LENGTH_SHORT).show();
            finish(); // Optionally close this activity if the list is empty.
        }
    }
}

