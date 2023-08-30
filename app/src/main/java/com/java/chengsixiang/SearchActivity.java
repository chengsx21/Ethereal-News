package com.java.chengsixiang;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private final int maxTextLimit = 40;
    private EditText searchText;
    private TextView startDateTime;
    private TextView endDateTime;
    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setBackButton();
        setNewsCategorySpinner();
        setStartDateTime();
        setEndDateTime();
        setSubmitButton();
    }

    public void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }

    private void setNewsCategorySpinner() {
        Spinner newsCategorySpinner = findViewById(R.id.news_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this, R.array.news_categories, R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsCategorySpinner.setAdapter(adapter);
    }

    private void setStartDateTime() {
        startDateTime = findViewById(R.id.selected_date_time_1);
        Button datePicker = findViewById(R.id.date_picker_button_1);
        datePicker.setOnClickListener(v -> showDatePickerDialog(startDateTime, startCalendar));
        Button timePicker = findViewById(R.id.time_picker_button_1);
        timePicker.setOnClickListener(v -> showTimePickerDialog(startDateTime, startCalendar));
    }

    private void setEndDateTime() {
        endDateTime = findViewById(R.id.selected_date_time_2);
        Button datePicker = findViewById(R.id.date_picker_button_2);
        datePicker.setOnClickListener(v -> showDatePickerDialog(endDateTime, endCalendar));
        Button timePicker = findViewById(R.id.time_picker_button_2);
        timePicker.setOnClickListener(v -> showTimePickerDialog(endDateTime, endCalendar));
    }

    private void showDatePickerDialog(TextView selectedDateTime, Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateSelectedDateTime(selectedDateTime, calendar);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(TextView selectedDateTime, Calendar calendar) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    updateSelectedDateTime(selectedDateTime, calendar);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateSelectedDateTime(TextView selectedDateTime, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        selectedDateTime.setText(dateFormat.format(calendar.getTime()));
    }

    private void setSubmitButton() {
        searchText = findViewById(R.id.search_text);
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            String text = searchText.getText().toString();
            if (text.isEmpty()) {
                showToast("搜索内容不能为空");
                return;
            }
            if (text.length() > maxTextLimit) {
                showToast("搜索内容过长");
                return;
            }
            if (startCalendar.compareTo(endCalendar) > 0) {
                showToast("搜索起始时间不能晚于截止时间");
                return;
            }
            Bundle bundle = new Bundle();
            startSearchResultActivity(bundle);
        });
    }

    private void startSearchResultActivity(Bundle bundle) {
        bundle.putString("words", searchText.getText().toString());
        bundle.putString("startDate", startDateTime.getText().toString());
        bundle.putString("endDate", endDateTime.getText().toString());
        bundle.putString("categories", ((Spinner) findViewById(R.id.news_category_spinner)).getSelectedItem().toString());
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtras(bundle);
        Log.d("SearchActivity", bundle.toString());
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
