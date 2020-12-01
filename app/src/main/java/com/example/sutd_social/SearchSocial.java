package com.example.sutd_social;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class SearchSocial extends AppCompatActivity {

    private static final String TAG = "SearchSocial"; // logging
    ArrayAdapter<String> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_social);

        // https://www.youtube.com/watch?v=pM1fAmUQn8g
        ListView listView = findViewById(R.id.search_view);
        ArrayList<String> userList = new ArrayList<>();

        // Get list of users:
        userList.add("Melody"); userList.add("Kai Xun"); userList.add("Darren");
        userList.add("Jun Kai");userList.add("Le Xuan"); userList.add("Ngai Man");

        // TODO: Can implement custom list item layout to look nicer
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);

        listView.setAdapter(arrayAdapter);
        Log.d(TAG, "onCreateOptionsMenu: Generating arrayAdapter view");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Search menu bar
        Log.d(TAG, "onCreateOptionsMenu: Generating search view");
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search others on SUTD social");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}