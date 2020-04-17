package com.example.finalexam2;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Movie> {
    private Activity context;
    private List<Movie> movieList;

    public MovieListAdapter(Activity context, List<Movie> movieList) {
        super(context, R.layout.list_layout, movieList);
        this.context = context;
        this.movieList = movieList;
    }

    public MovieListAdapter(Context context, int resource,
                            List<Movie> objects,
                            Activity context1, List<Movie> movieList) {
        super(context, resource, objects);
        this.context = context1;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView name = listViewItem.findViewById(R.id.textViewName);
        TextView description = listViewItem.findViewById(R.id.textViewDescription);
        TextView link = listViewItem.findViewById(R.id.textViewLink);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        Movie movie = movieList.get(position);
        name.setText(movie.getName());
        description.setText((movie.getDescription()));
        link.setText(movie.getLink());
        return listViewItem;
    }

}

