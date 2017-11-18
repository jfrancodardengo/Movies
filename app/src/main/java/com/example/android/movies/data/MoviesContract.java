package com.example.android.movies.data;

import android.provider.BaseColumns;

/**
 * Created by Guto on 17/11/2017.
 */

public  class MoviesContract {

    public static final class MoviesEntry implements BaseColumns{
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_ID = "movieId";

    }

}
