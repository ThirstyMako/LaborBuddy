package com.example.jason.laborbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contraction> contractionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContractionAdapter cAdapter;
    private boolean contractionActive;
    private Stopwatch duration;
    private Stopwatch frequency;
    private Contraction contraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordContraction();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        cAdapter = new ContractionAdapter(contractionList);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(cLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cAdapter);

        contractionActive = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        super.onOptionsItemSelected(item);

        //Switch statement to handle all menu items selected, and send them to the correct function.
        switch (item.getItemId()) {

            case R.id.delete:

                //Ask user if they are sure they want to delete.
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Clicking \"Yes\" will delete all contractions")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAll();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;

            default:
                return false;

        }

    }



    public void recordContraction() {

        if (contractionActive) {

            endContraction();

        } else {

            beginContraction();

        }

        contractionActive = !contractionActive;

    }

    public void beginContraction() {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

        //In case this is the first contraction.
        if (frequency != null) {

            long frequencyTimeInSeconds = frequency.getElapsedTimeSecs();
            frequency.stop();
            contraction.setFrequency("Freq: " + parseTime(frequencyTimeInSeconds));
            contractionList.add(contraction);
            cAdapter.notifyDataSetChanged();

        }

        contraction = new Contraction(sdf.format(new Date()), "", "");
        duration = new Stopwatch();
        duration.start();

        frequency = new Stopwatch();
        frequency.start();

    }

    public void endContraction() {

        long durationTimeInSeconds = duration.getElapsedTimeSecs();
        duration.stop();
        contraction.setDuration("Dur: " + parseTime(durationTimeInSeconds));


    }

    public String parseTime(long time) {

        long minutes = time / 60;

        long seconds = time % 60;

        String secondString;

        if (seconds < 10) {

            secondString = "0" + seconds;

        } else {

            secondString = Long.toString(seconds);

        }

        return Long.toString(minutes) + ":" + secondString;

    }


    //Function to remove all contractions and clear the recyclerView.  NEED FUNCTIONALITY FOR SQL.
    public void deleteAll() {

        //Stop both stopwatches and set them both to null, and set contractionActive boolean to false.
        frequency.stop();
        frequency = null;
        duration.stop();
        duration = null;
        contractionActive = false;

        //Clear the list, and notify the recyclerview adapter that the list has changed.
        contractionList.clear();
        cAdapter.notifyDataSetChanged();

    }

}


