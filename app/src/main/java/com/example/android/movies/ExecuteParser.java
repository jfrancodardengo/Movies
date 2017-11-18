package com.example.android.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Guto on 17/11/2017.
 */

public class ExecuteParser extends AppCompatActivity {
    RecyclerView recyclerView;
    private String jsonURL;

    public ExecuteParser(String jsonData, RecyclerView recyclerView) {
        this.jsonURL = jsonData;
        this.recyclerView = recyclerView;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(0,null,new JSONParser(this,jsonURL,recyclerView));
    }
}
