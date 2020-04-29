package com.example.aalboxapp;

import java.util.List;


public class PostFilter {

    private List<String> selectedCategories;
    private String selectedTab;

    public PostFilter(List<String> selectedCategories, String selectedTab) {
        this.selectedCategories = selectedCategories;
        this.selectedTab = selectedTab;
    }

    public List<String> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<String> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }
}
