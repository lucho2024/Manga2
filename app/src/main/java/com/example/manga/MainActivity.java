package com.example.manga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.manga.objetos.caratula;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<caratula> lista;
    AdaptadorCaratula adapter;
    caratula actual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.idrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        new scrape().execute();
    }
    class scrape extends AsyncTask<Void, Void, ArrayList<caratula>>
    {

        @Override
        protected ArrayList<caratula> doInBackground(Void... voids) {

            try {

                ArrayList<caratula> lista = new ArrayList<>();

                Document html = Jsoup.connect("https://tmofans.com").get();

                Elements img = html.select("div[class^=thumbnail book]");
                Pattern query = Pattern.compile("\'(.*)\'");
                for (int i = 0; i < img.size(); i++)
                {
                     actual = new caratula();
                    Matcher test = query.matcher(
                            img.eq( i ).select("style").html()
                    );
                    if ( test.find() ) {
                        Log
                                .d(
                                        "test_scrape",
                                        test.group( 1 )
                                );
                        actual.setFoto(
                                test.group( 1 )
                        );
                        lista.add( actual );
                    }
                }


                Elements titulo = html.select("h4[class=text-truncate]");
                for (int i = 0; i < titulo.size(); i++)
                {
                    if ( i < lista.size() ) {
                        lista.get(i).setNombre(
                                titulo.eq(i).text()
                        );
                        Log
                                .d(
                                        "test_scrape",
                                        titulo.eq(i).text()
                                );
                    }
                }
                return lista;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<caratula> caratulas) {
            ConsolaDebug( "lista de objetos resultado " + caratulas.size() );
            recycler.setAdapter( new AdaptadorCaratula( caratulas ) );
        }
    }

    private void ConsolaDebug(String msg)
    {
        Log.d("test_scrape", msg);
    }

}
