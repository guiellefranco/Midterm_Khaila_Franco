package com.example.multiplication_table;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnGenerate, btnHistory;
    private ListView listView;
    private ArrayList<String> tableList;
    private ArrayAdapter<String> adapter;
    public static ArrayList<Integer> historyList = new ArrayList<>(); // shared for Activity 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);
        listView = findViewById(R.id.listViewTable);

        tableList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableList);
        listView.setAdapter(adapter);

        // Generate table when button clicked
        btnGenerate.setOnClickListener(v -> generateTable());

        // Handle delete item on click
        listView.setOnItemClickListener((parent, view, position, id) -> confirmDelete(position));

        // Navigate to History screen
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void generateTable() {
        String numText = etNumber.getText().toString().trim();
        if (numText.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        int num = Integer.parseInt(numText);
        tableList.clear();
        for (int i = 1; i <= 10; i++) {
            tableList.add(num + " × " + i + " = " + (num * i));
        }
        adapter.notifyDataSetChanged();

        if (!historyList.contains(num)) {
            historyList.add(num);
        }
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Row")
                .setMessage("Do you want to delete this row?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String removed = tableList.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Deleted: " + removed, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}