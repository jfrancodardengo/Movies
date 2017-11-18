package com.example.android.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Guto on 15/10/2017.
 */

//public class JSONDownloader extends AsyncTask<Void, Void, String> {
public class JSONDownloader extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    Context context;
    private String jsonURL;
    RecyclerView recyclerView;

    //create a constant to identify loader
    private static final int LOADER=1;

    public JSONDownloader(Context context, String jsonURL, RecyclerView recyclerView) {
        this.context = context;
        this.jsonURL = jsonURL;
        this.recyclerView = recyclerView;
    }

    public JSONDownloader() {}

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(LOADER,null,this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                if(args == null) return;

                if (isNetworkAvailable(context)) {
                    Log.v("Ocorreu conexão com a ", "internet");
                }
            }

            @Override
            public String loadInBackground() {
                return download();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data.startsWith("Error")) {
            String error = data;
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        } else {
            Log.v("ANSWER TO DOWNLOAD: ",data);
            //PARSER
            new JSONParser(context,data,recyclerView);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    //DOWNLOAD
    private String download() {
        Object connection= Connector.connect(jsonURL);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }
        try
        {
            HttpURLConnection con= (HttpURLConnection) connection;
            if(con.getResponseCode()==con.HTTP_OK)
            {
                //GET INPUT FROM STREAM
                InputStream is=new BufferedInputStream(con.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer jsonData=new StringBuffer();
                //READ
                while ((line=br.readLine()) != null)
                {
                    jsonData.append(line+"\n");
                }
                //CLOSE RESOURCES
                br.close();
                is.close();
                //RETURN JSON
                return jsonData.toString();
            }else
            {
                return "Error "+con.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();
        }
}

    boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

