package com.erik.inputdialog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class KontakDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kontak.db";
    public static final String TABLE_NAME = "kontak";

    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "nama";
    public static final String COLUMN_NAME_PHONE = "nohp";

    public KontakDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_NAME_NAME + " TEXT, " + COLUMN_NAME_PHONE + " TEXT)";

        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addKontak(Kontak kontak) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_NAME, kontak.getName());
        values.put(COLUMN_NAME_PHONE, kontak.getNumber());

        long id = db.insert(TABLE_NAME, null, values);

        return id;
    }

    public void deleteKontak(String nama){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "nama='" + nama + "'", null);
    }

    public void updateKontak(Kontak kontak, String nama, String nohp){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dataku = new ContentValues();

        dataku.put("nohp", nohp);
        dataku.put("nama", nama);
        db.update(TABLE_NAME, dataku, "nama='" + kontak.getName() + "'", null);
    }

    public ArrayList<Kontak> getAllItems() {
        ArrayList<Kontak> kontaks = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Kontak kontak = new Kontak(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE))
                );

                kontaks.add(kontak);
            } while (cursor.moveToNext());
        }

        return kontaks;
    }

    public ArrayList<Kontak> getItemsByName(String name) {
        ArrayList<Kontak> kontaks = new ArrayList<>();

        String SELECT_QUERY = "select * from kontak where nama like '%" + name + "%'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Kontak kontak = new Kontak(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE))
                );

                kontaks.add(kontak);
            } while (cursor.moveToNext());
        }

        return kontaks;
    }

}
