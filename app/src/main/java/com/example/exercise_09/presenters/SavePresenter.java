package com.example.exercise_09.presenters;

import com.example.exercise_09.DataBase;
import com.example.exercise_09.ISavePresenter;
import com.example.exercise_09.ISaveView;
import com.example.exercise_09.model.objects.Product;

public class SavePresenter implements ISavePresenter {
    private DataBase dataBase;

    private ISaveView iSaveView;

    public SavePresenter(ISaveView iSaveView) {
        this.iSaveView = iSaveView;
        dataBase = new DataBase(this);
    }

    public void clickSave(Product product) {
        dataBase.clickSave(product);
    }

    @Override
    public void onSave(Product product) {
        iSaveView.onFail(product);
    }

    @Override
    public void unSave(Product product) {
        iSaveView.onSuccess(product);
    }

}
