package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.data.MoviesDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context = MainActivity.this;
    RecyclerView recyclerView;
    String apiKey = com.example.android.movies.BuildConfig.MOVIES_KEY;
    String jsonURLPopular="https://api.themoviedb.org/3/movie/popular?api_key="+apiKey+"&language=pt-BR";
    String jsonURLTopRated="https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey+"&language=pt-BR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

        Log.v("URL SEND TO SERVER: ",jsonURLPopular);

        new JSONDownloader(context,jsonURLPopular,recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicado = item.getItemId();
        if (itemClicado==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else if (itemClicado == R.id.action_votes){

            new JSONDownloader(context,jsonURLTopRated,recyclerView);
            return true;
        }
        new JSONDownloader(context,jsonURLPopular,recyclerView);
        return super.onOptionsItemSelected(item);
    }

}
