package com.example.exercise_09;

import com.example.exercise_09.model.objects.Product;

public class DataBase {
    ISavePresenter iSavePresenter;

    public DataBase(ISavePresenter iSavePresenter) {
        this.iSavePresenter = iSavePresenter;
    }

    public void clickSave(Product product) {
        if (product.getCheck_like() == 0) {
            iSavePresenter.unSave(product);
        } else if (product.getCheck_like() == 1) {
            iSavePresenter.onSave(product);
        }
    }

}
