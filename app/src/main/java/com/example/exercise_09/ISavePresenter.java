package com.example.exercise_09;

import com.example.exercise_09.model.objects.Product;

public interface ISavePresenter {
    void onSave(Product product);
    void unSave(Product product);
}