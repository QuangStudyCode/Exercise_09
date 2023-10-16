package com.example.exercise_09;

import com.example.exercise_09.model.objects.Product;

public interface ISaveView {
    void onSuccess(Product product);
    void onFail(Product product);
}
