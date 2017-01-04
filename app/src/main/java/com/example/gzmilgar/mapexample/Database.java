package com.example.gzmilgar.mapexample;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gizem İlgar on 21.12.2016.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";
    private static final String TABLE_NAME = "konum_listesi";
    private static String KONUM_ADI = "konum_adi";
    private static String KONUM_ID = "id";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KONUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KONUM_ADI + " TEXT,"
                +  ")";
        db.execSQL(CREATE_TABLE);
    }

    public void konumSil(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KONUM_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void konumEkle(String konum_adi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KONUM_ADI, konum_adi);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<String, String> konumDetay(int id){
        //Databeseden id si belli olan row u çekmek için.
        //Bu methodda sadece tek row değerleri alınır.
        //HashMap bir çift boyutlu arraydir.anahtar-değer ikililerini bir arada tutmak için tasarlanmıştır.
        //map.put("x","300"); mesala burda anahtar x değeri 300.

        HashMap<String,String> konum = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            konum.put(KONUM_ADI, cursor.getString(1));
        }
        cursor.close();
        db.close();
        return konum;
    }

    public  ArrayList<HashMap<String, String>> konumlar(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> konumlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                konumlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return konumlist;
    }

    public void konumDuzenle(String konum_adi,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KONUM_ADI, konum_adi);

        db.update(TABLE_NAME, values, KONUM_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}