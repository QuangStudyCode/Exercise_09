package com.example.exercise_09.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.exercise_09.model.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "product_Api";
    public static final int VERSION = 1;
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_CHECK = "check_like";
    public static final String PRODUCT_TITLE = "title";
    public static final String PRODUCT_DES = "description";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_DISCOUNT = "discountPercentage";
    public static final String PRODUCT_RATING = "rating";
    public static final String PRODUCT_STOCK = "stock";
    public static final String PRODUCT_BRAND = "brand";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_THUMBNAIL = "thumbnail";
    public static final String PRODUCT_IMAGES = "images";

    public static final String DATABASE_NAME = "product_api.db";

    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + PRODUCT_ID + " INTEGER PRIMARY KEY UNIQUE,"
                + PRODUCT_CHECK + " INTEGER NOT NULL,"
                + PRODUCT_TITLE + " TEXT NOT NULL,"
                + PRODUCT_DES + " TEXT NOT NULL,"
                + PRODUCT_PRICE + " TEXT NOT NULL,"
                + PRODUCT_DISCOUNT + " TEXT NOT NULL,"
                + PRODUCT_RATING + " TEXT NOT NULL,"
                + PRODUCT_STOCK + " TEXT NOT NULL,"
                + PRODUCT_BRAND + " TEXT NOT NULL,"
                + PRODUCT_CATEGORY + " TEXT NOT NULL,"
                + PRODUCT_THUMBNAIL + " TEXT NOT NULL,"
                + PRODUCT_IMAGES + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addProductFromAPI(Product product) {
        if (product != null) {
            SQLiteDatabase database = this.getWritableDatabase();

            // Kiểm tra sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
            String selection = PRODUCT_ID + "=?";
            String[] selectionArgs = {String.valueOf(product.getId())};
            Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                // Sản phẩm đã tồn tại, bỏ qua việc chèn
                cursor.close();
                database.close();
                return false;
            }

            // Sản phẩm chưa tồn tại, tiến hành chèn vào cơ sở dữ liệu
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCT_ID, product.getId());
            contentValues.put(PRODUCT_CHECK, product.getCheck_like());
            contentValues.put(PRODUCT_TITLE, product.getTitle());
            contentValues.put(PRODUCT_DES, product.getDescription());
            contentValues.put(PRODUCT_PRICE, product.getPrice());
            contentValues.put(PRODUCT_DISCOUNT, product.getDiscountPercentage());
            contentValues.put(PRODUCT_RATING, product.getRating());
            contentValues.put(PRODUCT_STOCK, product.getStock());
            contentValues.put(PRODUCT_BRAND, product.getBrand());
            contentValues.put(PRODUCT_CATEGORY, product.getCategory());
            contentValues.put(PRODUCT_THUMBNAIL, product.getThumbnail());

            long response = database.insert(TABLE_NAME, null, contentValues);
            database.close();

            return response != -1;
        }
        return false;
    }

    @SuppressLint("Range")
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
            product.setTitle(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
            product.setDescription(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
            product.setPrice(cursor.getInt(cursor.getColumnIndex(PRODUCT_PRICE)));
            product.setDiscountPercentage(cursor.getDouble(cursor.getColumnIndex(PRODUCT_DISCOUNT)));
            product.setRating(cursor.getDouble(cursor.getColumnIndex(PRODUCT_RATING)));
            product.setStock(cursor.getInt(cursor.getColumnIndex(PRODUCT_STOCK)));
            product.setBrand(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND)));
            product.setThumbnail(cursor.getString(cursor.getColumnIndex(PRODUCT_THUMBNAIL)));

            productList.add(product);
        }
        sqLiteDatabase.close();
        return productList;
    }

    public boolean deleteAllProducts() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rowsAffected = sqLiteDatabase.delete(TABLE_NAME, null, null);
        sqLiteDatabase.close();

        return rowsAffected > 0;
    }

    public void deleteProduct(int idProduct) {
        if (idProduct >= 0) {
            SQLiteDatabase database = getWritableDatabase();
            String whereClause = PRODUCT_ID + "=?";

            String[] whereArg = {idProduct + ""};
            database.delete(TABLE_NAME, whereClause, whereArg);
        }
    }

    public void updateCheckLikeProduct(Integer productID) {
        if (productID >= 0) {
            SQLiteDatabase database = this.getWritableDatabase();
            String whereClause = PRODUCT_ID + "=?";
            String[] whereArgs = {String.valueOf(productID)};

            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCT_CHECK, 1);

            int rowsAffected = database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Log.d("TAG", "updateCheckLikeProduct: " + productID);
            } else {
                Log.d("TAG", "updateCheckLikeProduct: false");
            }

            database.close();
        }
    }

    @SuppressLint("Range")
    public int getCheckLikeProduct(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int checkLike = 0;

        String[] projection = {
                PRODUCT_CHECK
        };

        String selection = PRODUCT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            checkLike = cursor.getInt(cursor.getColumnIndex(PRODUCT_CHECK));
        }

        cursor.close();
        db.close();

        return checkLike;
    }
}
