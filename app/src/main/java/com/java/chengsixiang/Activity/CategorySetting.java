package com.java.chengsixiang.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.R;
import com.java.chengsixiang.Utils.CategoryHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CategorySetting extends AppCompatActivity {
    private ArrayList<String> availableCategories;
    private ArrayList<String> selectedCategories;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        context = this;
        setCategories();
        setBackButton();
        setSaveButton();
        setRecyclerView();
        setTouchHelper();
    }

    private void setCategories() {
        SharedPreferences sharedCategories = getSharedPreferences("categories", MODE_PRIVATE);
        String availableString = sharedCategories.getString("available", "");
        String selectedString = sharedCategories.getString("selected", "");

        if (selectedString.isEmpty())
            selectedCategories = new ArrayList<>(Arrays.asList("全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"));
        else
            selectedCategories = new ArrayList<>(Arrays.asList(selectedString.split(",")));
        if (availableString.isEmpty())
            availableCategories = new ArrayList<>();
        else
            availableCategories = new ArrayList<>(Arrays.asList(availableString.split(",")));
    }

    private void setBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void setSaveButton() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(view -> {
            SharedPreferences sharedCategories = getSharedPreferences("categories", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedCategories.edit();
            editor.putString("selected", TextUtils.join(",", selectedCategories));
            editor.putString("available", TextUtils.join(",", availableCategories));
            editor.apply();
            Toast.makeText(context, "修改分类成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);
    }

    private void setTouchHelper() {
        ItemTouchHelper.Callback callback = new CategoryHelper(categoryAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
            implements CategoryHelper.CategoryHelperAdapter {
        private static final int TYPE_SELECTED_CATEGORY = 0;
        private static final int TYPE_AVAILABLE_CATEGORY = 1;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == TYPE_SELECTED_CATEGORY) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_category, parent, false);
                return new SelectedCategoryHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_category, parent, false);
                return new AvailableCategoryHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof SelectedCategoryHolder) {
                String channel = selectedCategories.get(position);
                ((SelectedCategoryHolder) holder).bind(channel);
            } else if (holder instanceof AvailableCategoryHolder) {
                String channel = availableCategories.get(position - selectedCategories.size());
                ((AvailableCategoryHolder) holder).bind(channel);
            }
        }

        @Override
        public int getItemCount() {
            return selectedCategories.size() + availableCategories.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < selectedCategories.size())
                return TYPE_SELECTED_CATEGORY;
            else
                return TYPE_AVAILABLE_CATEGORY;
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < selectedCategories.size() && toPosition < selectedCategories.size()) {
                if (fromPosition == 0 || toPosition == 0) {
                    notifyDataSetChanged();
                    return;
                }
                Collections.swap(selectedCategories, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
            } if (fromPosition >= selectedCategories.size() && toPosition >= selectedCategories.size()) {
                Collections.swap(availableCategories, fromPosition - selectedCategories.size(), toPosition - selectedCategories.size());
                notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemDismiss(int position) {
            if (position < selectedCategories.size()) {
                if (position == 0) {
                    notifyDataSetChanged();
                    return;
                }
                String category = selectedCategories.remove(position);
                availableCategories.add(category);
            }
            notifyDataSetChanged();
        }

        private class SelectedCategoryHolder extends RecyclerView.ViewHolder {
            private final TextView categoryName;

            SelectedCategoryHolder(View itemView) {
                super(itemView);
                categoryName = itemView.findViewById(R.id.category_text);
            }

            void bind(String category) {
                categoryName.setText(category);
            }
        }

        private class AvailableCategoryHolder extends RecyclerView.ViewHolder {
            private final TextView categoryName;

            AvailableCategoryHolder(View itemView) {
                super(itemView);
                categoryName = itemView.findViewById(R.id.category_text);
                ImageButton addButton = itemView.findViewById(R.id.category_button);
                addButton.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String category = availableCategories.remove(position - selectedCategories.size());
                        selectedCategories.add(category);
                        notifyDataSetChanged();
                    }
                });
            }

            void bind(String category) {
                categoryName.setText(category);
            }
        }
    }
}
