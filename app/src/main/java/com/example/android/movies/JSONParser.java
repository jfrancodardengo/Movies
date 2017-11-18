package com.example.android.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Guto on 15/10/2017.
 */

//public class JSONParser  extends AsyncTask<Void,Void,Boolean>{
public class JSONParser extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean>{
    Context context;
    String jsonData;
    String imageURL="http://image.tmdb.org/t/p/w342";
    RecyclerView recyclerView;
    ArrayList<Movie> movies =new ArrayList<>();


    //create a constant to identify loader
    private static final int LOADER=1;

    public JSONParser(Context context, String jsonData, RecyclerView recyclerView) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(LOADER,null,this).forceLoad();

        recyclerView.setAdapter(new MovieAdapter(this,movies));
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id,final Bundle args) {
        return new AsyncTaskLoader<Boolean>(context) {

            @Override
            protected void onStartLoading() {
                if(args == null) return;

                forceLoad();
            }

            @Override
            public Boolean loadInBackground() {
                JSON json = new JSON(jsonData,imageURL, movies);
                return json.parse();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        if(data)
        {
            //BIND
//            recyclerView.setAdapter(new MovieAdapter(context, movies));
        }else
        {
            Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

}

