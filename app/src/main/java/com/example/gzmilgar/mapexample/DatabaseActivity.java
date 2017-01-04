package com.example.gzmilgar.mapexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DatabaseActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<HashMap<String, String>> konum_liste;
    String konum_adlari[];
    int konum_idler[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    }

    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
        konum_liste = db.konumlar();//konum listesini alıyoruz
        if(konum_liste.size()==0){//konum listesi boşsa
            Toast.makeText(getApplicationContext(), "Henüz konum Eklenmemiş.", Toast.LENGTH_LONG).show();
        }else{
            konum_adlari = new String[konum_liste.size()]; // konum adlarını tutucamız string arrayi olusturduk.
            konum_idler = new int[konum_liste.size()]; // konum id lerini tutucamız string arrayi olusturduk.
            for(int i=0;i<konum_liste.size();i++){
                konum_adlari[i] = konum_liste.get(i).get("konum_adi");
                //konum_liste.get(0) bize arraylist içindeki ilk hashmap arrayini döner. Yani tablomuzdaki ilk satır değerlerini
                //konum_liste.get(0).get("konum_adi") //bize arraylist içindeki ilk hashmap arrayin anahtarı konum_adi olan value döner

                konum_idler[i] = Integer.parseInt(konum_liste.get(i).get("id"));
                //Yukarıdaki ile aynı tek farkı değerleri integer a çevirdik.
            }
            //konumları Listeliyoruz ve bu listeye listener atıyoruz
            lv = (ListView) findViewById(R.id.list_view);

            adapter = new ArrayAdapter(this, R.layout.list_item, R.id.konum_adi, konum_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    //Listedeki her hangibir yere tıklandıgında tıklanan satırın sırasını alıyoruz.
                    //Bu sıra id arraydeki sırayla aynı oldugundan tıklanan satırda bulunan konumın id sini alıyor ve konum detaya gönderiyoruz.
                    Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                    intent.putExtra("id", (int)konum_idler[arg2]);
                    startActivity(intent);
                }
            });
        }
    }
}