package com.example.multiplication_table;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private Button btnBack, btnClearAll;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> historyDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        btnBack = findViewById(R.id.btnBack);
        btnClearAll = findViewById(R.id.btnClearAll);

        // Convert historyList to an ArrayList of Strings
        historyDisplay = new ArrayList<>();
        for (int n : MainActivity.historyList) {
            historyDisplay.add(String.valueOf(n));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyDisplay);
        listViewHistory.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish()); // go back to first Activity

        // BONUS FEATURE
        btnClearAll.setOnClickListener(v -> {
            if (historyDisplay.isEmpty()) {
                Toast.makeText(this, "Nothing to clear!", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Clear All History")
                    .setMessage("Are you sure you want to delete all history?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        historyDisplay.clear();
                        MainActivity.historyList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "All history cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}