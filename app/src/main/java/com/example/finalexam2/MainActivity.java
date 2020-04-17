package com.example.finalexam2;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextMovieName;
    EditText editTextMovieDescription;
    EditText editTextLink;
    Button buttonAddMovie;
    DatabaseReference databaseMovies;
    ListView lvMovies;
    List<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseMovies = FirebaseDatabase.getInstance().getReference("movies");

        editTextMovieName = findViewById(R.id.editTextMovieName);
        editTextMovieDescription = findViewById(R.id.editTextMovieDescription);
        editTextLink = findViewById(R.id.editTextLink);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);
        lvMovies = findViewById(R.id.lvMovies);
        movieList = new ArrayList<>();


        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovie();
            }
        });

    }
    private void addMovie() {
        String movieName = editTextMovieName.getText().toString().trim();
        String description = editTextMovieDescription.getText().toString().trim();
        String link = editTextLink.getText().toString().trim();

        if (TextUtils.isEmpty(movieName)) {
            Toast.makeText(this, "You must enter a movie name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "You must enter a movie description.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(link)) {
            Toast.makeText(this, "You must enter a movie resource link.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = databaseMovies.push().getKey();
        Movie movie = new Movie(movieName, description, link);

        Task setValueTask = databaseMovies.child(id).setValue(movie);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,"Movie added.",Toast.LENGTH_LONG).show();

                editTextMovieName.setText("");
                editTextMovieDescription.setText("");
                editTextLink.setText("");
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseMovies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movieList.clear();
                for (DataSnapshot movieSnapshot : dataSnapshot.getChildren()) {
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    movieList.add(movie);
                }

                MovieListAdapter adapter = new MovieListAdapter(MainActivity.this, movieList);
                lvMovies.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


}
