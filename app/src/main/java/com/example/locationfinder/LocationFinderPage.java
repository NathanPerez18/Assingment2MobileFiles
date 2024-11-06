package com.example.locationfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LocationFinderPage extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText searchBar;
    private TextView addressText;
    private TextView latitudeText;
    private TextView longitudeText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_finder_page);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        searchBar = findViewById(R.id.search_bar);
        addressText = findViewById(R.id.address_value);
        latitudeText = findViewById(R.id.latitude_value);
        longitudeText = findViewById(R.id.longitude_value);
        searchButton = findViewById(R.id.search_button);

        // Set up back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LocationFinderPage.this, MainActivity.class);
            startActivity(intent);
        });

        // Set up search button click listener
        searchButton.setOnClickListener(v -> {
            String searchQuery = searchBar.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                searchLocation(searchQuery);
            } else {
                Toast.makeText(LocationFinderPage.this, "Please enter an address to search.", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize Add, Delete, and Update buttons
        Button addButton = findViewById(R.id.add_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button updateButton = findViewById(R.id.update_button);

        // Set up Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Set up Delete button click listener
        deleteButton.setOnClickListener(v -> showDeleteDialog());

        // Set up Update button click listener
        updateButton.setOnClickListener(v -> showUpdateDialog());
    }

    // Method to show Add dialog
    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Location");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_location, null);
        EditText editAddress = dialogView.findViewById(R.id.editAddress);
        EditText editLatitude = dialogView.findViewById(R.id.editLatitude);
        EditText editLongitude = dialogView.findViewById(R.id.editLongitude);

        builder.setView(dialogView);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String address = editAddress.getText().toString();
            double latitude = Double.parseDouble(editLatitude.getText().toString());
            double longitude = Double.parseDouble(editLongitude.getText().toString());
            dbHelper.insertData(address, latitude, longitude);
            Toast.makeText(this, "Location added.", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Method to show Delete dialog
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Location");

        EditText editAddress = new EditText(this);
        editAddress.setHint("Enter Address to delete");
        builder.setView(editAddress);

        builder.setPositiveButton("Delete", (dialog, which) -> {
            String address = editAddress.getText().toString().trim();
            if (!address.isEmpty()) {
                dbHelper.deleteData(address);
                Toast.makeText(this, "Location deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter an address.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    // Method to show Update dialog
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Location");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_location, null);
        EditText editId = dialogView.findViewById(R.id.editId);
        EditText editNewAddress = dialogView.findViewById(R.id.editNewAddress);
        EditText editNewLatitude = dialogView.findViewById(R.id.editNewLatitude);
        EditText editNewLongitude = dialogView.findViewById(R.id.editNewLongitude);

        builder.setView(dialogView);
        builder.setPositiveButton("Update", (dialog, which) -> {
            int id = Integer.parseInt(editId.getText().toString());
            String newAddress = editNewAddress.getText().toString();
            double newLatitude = Double.parseDouble(editNewLatitude.getText().toString());
            double newLongitude = Double.parseDouble(editNewLongitude.getText().toString());
            dbHelper.updateData(id, newAddress, newLatitude, newLongitude);
            Toast.makeText(this, "Location updated.", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Search location and populate text fields
    @SuppressLint("Range")
    private void searchLocation(String address) {
        Cursor cursor = dbHelper.searchLocation(address);

        if (cursor != null && cursor.moveToFirst()) {
            addressText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS)));
            latitudeText.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE))));
            longitudeText.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE))));
            cursor.close();
        } else {
            Toast.makeText(this, "Location not found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // Close the database helper when activity is destroyed
        super.onDestroy();
    }
}
