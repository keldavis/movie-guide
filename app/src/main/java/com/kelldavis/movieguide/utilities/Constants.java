package com.kelldavis.movieguide.utilities;

import com.kelldavis.movieguide.BuildConfig;
import com.kelldavis.movieguide.ui.MainActivity;

public class Constants {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String MOVIE = "MOVIE";

    // API calls
    public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w780/";
    public static final String IMAGE_URL_SIZE = IMAGE_BASE_URL + IMAGE_SIZE;
    public static final String SCHEMA = "https";
    static final String LANGUAGE = "en-US";

    // save and restore default sort order on activity state changes
    public static final String MOST_POPULAR_OPTION_CHECKED = "MOST_POPULAR_OPTION_CHECKED";
    public static final String TOP_RATED_OPTION_CHECKED = "TOP_RATED_OPTION_CHECKED";

    // define item type to load on a page, grid item, or progress bar
    public static final int ITEM = 0;
    public static final int PROGRESS = 1;

    // page tab titles
    public static final String TAB_INFO = "INFO";
    public static final String TAB_CAST = "CAST";
    public static final String TAB_REVIEWS = "REVIEWS";

    // default max lines
    public static final int MAX_LINES = 3;

    // define image set up options such as default placeholders and error images
    public static final String POSTER_IMG = "poster";
    public static final String BACKDROP_IMG = "backdrop";
    public static final String CAST_IMG = "cast";

    // item view cache size for RecyclerView
    public static final int ITEM_VIEW_CACHE_SIZE = 10;

    // layout types for RecyclerView
    public static final int GRID_LAYOUT = 0;
    public static final int LINEAR_LAYOUT_VERTICAL = 1;
    public static final int LINEAR_LAYOUT_HORIZONTAL = 2;

    // for handling youtube movie trailers
    public static final String YOUTUBE_VID_BASE_URI = "https://youtube.com/watch?v=";
    public static final String YOUTUBE_IMG_BASE_URI = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_IMG_EXTENSION = "/mqdefault.jpg";
    public static final String SITE_FILTER_YOUTUBE = "youtube";

    // tmdb profile url and google play store link
    public static final String TMDB_MOVIE_BASE_URI = "https://www.themoviedb.org/movie/";
    public static final String PLAYSTORE_BASE_URI = "market://search?q=";
    public static final String PLAYSTORE_QUERY_PARAMETER_CATEGORY = "c";
    public static final String PLAYSTORE_QUERY_VALUE_CATEGORY = "movies";

    // append to response query parameter value
    public static final String APPEND_TO_RESPONSE_VALUE = "images,videos,releases";

    //save page numbers to restore paginated data
    public static final String MOST_POPULAR_START_PAGE = "MOST_POPULAR_START_PAGE";
    public static final String TOP_RATED_START_PAGE = "TOP_RATED_START_PAGE";

    //key to save data set
    public static final String MOVIES_LIST = "MOVIES_LIST";
    //define key for saving RecyclerView Layout Manager state
    public static final String RECYCLER_VIEW_LAYOUT_MANAGER_STATE = "LAYOUT_MANAGER_STATE";
}

