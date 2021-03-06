package com.kelldavis.movieguide.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kelldavis.movieguide.R;
import com.kelldavis.movieguide.adapter.MovieVideoAdapter;
import com.kelldavis.movieguide.listener.MovieApiClient;
import com.kelldavis.movieguide.model.Credits;
import com.kelldavis.movieguide.model.Crew;
import com.kelldavis.movieguide.model.Movie;
import com.kelldavis.movieguide.model.Video;
import com.kelldavis.movieguide.utilities.MovieApiService;
import com.kelldavis.movieguide.utilities.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelldavis.movieguide.utilities.Constants.API_KEY;
import static com.kelldavis.movieguide.utilities.Constants.LINEAR_LAYOUT_HORIZONTAL;
import static com.kelldavis.movieguide.utilities.Constants.MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment {

    @BindView(R.id.tmdbRating)
    TextView tmdbRating;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.popularity)
    TextView popularity;
    @BindView(R.id.originalLanguage)
    TextView originalLanguage;
    @BindView(R.id.overview)
    TextView overviewTextView;
    @BindView(R.id.director)
    TextView directorTextView;
    @BindView(R.id.release_date)
    TextView releaseDateTextView;
    @BindView(R.id.homepage)
    TextView homepage;
    @BindView(R.id.originalTitle)
    TextView originalTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    private List<Crew> crewList;
    private List<Video> videoList;


    public MovieInfoFragment() {
        // Required empty public constructor
    }

    /**
     * inflates the view for the fragment
     *
     * @param inflater           reference to inflater service
     * @param container          parent for the fragment
     * @param savedInstanceState reference to bundle object that can be used to save activity states
     * @return inflated view for fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_info, container, false);
    }

    /**
     * called after onCreateView returns - resolve references to child views here
     *
     * @param view               reference to created view that can be modified
     * @param savedInstanceState reference to bundle object that can be used to save activity states
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        //set up RecyclerView - define caching properties and default animator
        Utils.setupRecyclerView(getContext(), recyclerView, LINEAR_LAYOUT_HORIZONTAL);

        //initialize data set and set up the adapter
        videoList = new ArrayList<>();
        final MovieVideoAdapter adapter = new MovieVideoAdapter(getContext(), videoList);
        recyclerView.setAdapter(adapter);

        //get movie object
        Movie movie = getArguments().getParcelable(MOVIE);

        //initialize data set
        crewList = new ArrayList<>();
        //fetch list of originalLanguage code and corresponding names
        HashMap<String, String> languageMap = Utils.fetchAllLanguages(getContext());

        //initialize retrofit client and call object that wraps the response
        MovieApiClient client = MovieApiService.getClient().create(MovieApiClient.class);
        //invoke movie credits call passing the movie id and API KEY
        Call<Credits> creditResultsCall = client.getMovieCredits(((Movie) getArguments().getParcelable(MOVIE)).getMovieId(), API_KEY);
        //invoke API call asynchronously
        creditResultsCall.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(@NonNull Call<Credits> call, @NonNull Response<Credits> response) {
                //verify if the response body or the fetched results are empty/null
                if (response.body() == null || response.body().getCrew() == null || response.body().getCrew().size() == 0) {
                    return;
                }

                //update data set, update the views accordingly
                crewList.addAll(response.body().getCrew());
                directorTextView.setText("");
                for (int i = 0; i < crewList.size(); i++) {
                    if (crewList.get(i).getJob().equalsIgnoreCase("director")) {
                        directorTextView.append(crewList.get(i).getName());
                        if (i < crewList.size() - 1 && crewList.get(i + 1).getJob().equalsIgnoreCase("director")) {
                            directorTextView.append("\n");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Credits> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.error_movie_director, Toast.LENGTH_SHORT).show();
            }
        });

        //display trailer thumbnails
        if (movie.getVideos() != null && movie.getVideos().getResults() != null && movie.getVideos().getResults().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            videoList.addAll(movie.getVideos().getResults());
            adapter.notifyDataSetChanged();
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


        //set up view data
        overviewTextView.setText(movie.getOverview());
        releaseDateTextView.setText(movie.getReleaseDate());
        String rating = movie.getVoteAverage() == 0 ? getString(R.string.no_ratings) : DecimalFormat.getNumberInstance().format(movie.getVoteAverage()).concat("/10");
        tmdbRating.setText(rating);
        ratingBar.setRating((float) (movie.getVoteAverage() / 2f));
        popularity.setText(DecimalFormat.getNumberInstance().format(movie.getPopularity()));
        originalLanguage.setText(languageMap.get(movie.getOriginalLanguage()));
        if (!TextUtils.isEmpty(movie.getHomepage())) {
            homepage.setText(movie.getHomepage());
        }
        if (!TextUtils.isEmpty(movie.getOriginalTitle())) {
            originalTitle.setText(movie.getOriginalTitle());
        }
    }


    /**
     * return new instance of fragment with movie data passed in as arguments
     *
     * @param movie reference to movie object set as one of fragment's arguments
     * @return instance of fragment
     */
    public static Fragment newInstance(Movie movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }
}

