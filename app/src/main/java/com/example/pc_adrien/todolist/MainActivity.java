package com.example.pc_adrien.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity
{
    ImageButton addButton;
    Intent PrefIntent;
    RecyclerView rv;
    InputStream fileInput = null;
    List<String> ToDoList = new ArrayList<>();
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final Intent myIntent = new Intent(MainActivity.this,contenerList.class);
        addButton = (ImageButton) findViewById(R.id.addButton);


        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(myIntent);
            }
        });

        rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter());

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_cell, parent, false);

            readFileIntoList("ToDoList.txt");
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position)
        {
            final String TodoSting = ToDoList.get(position);
            holder.display(TodoSting);
        }


        @Override
        public int getItemCount()
        {
            return ToDoList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            private final ImageButton deleteButton;
            private final TextView AFaire;

            public MyViewHolder(final View itemView)
            {
                super(itemView);
                AFaire = ((TextView) itemView.findViewById(R.id.TodoTextView));
                deleteButton = (ImageButton) itemView.findViewById(R.id.delete_imageButton);

                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = getAdapterPosition();
                        ToDoList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                });
            }

            public void display(String ToDo)
            {
                AFaire.setText(ToDo);
            }
         }
    }


    private void readFileIntoList (String fileName)
    {
        ToDoList.clear();

        try
        {
            fileInput = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String line;
            while ((line = reader.readLine()) != null)
            {
                ToDoList.add(line);
            }
            fileInput.close();
        }
        catch (IOException e)
        {
            e.getCause();
        }
    }

    public void onResume()
    {
        super.onResume();
        readFileIntoList("ToDoList.txt");

        super.onResume();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String color = sharedPref.getString("lisCouleurPref","FFFFFF");

        if(color.equalsIgnoreCase("Red"))
        {
            toolbar.setBackgroundColor(Color.RED);
            addButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            toolbar.setTitleTextColor(Color.BLACK);

        }
        if(color.equalsIgnoreCase("Blue"))
        {
            toolbar.setBackgroundColor(Color.BLUE);
            addButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
            toolbar.setTitleTextColor(Color.WHITE);


        }
        if(color.equalsIgnoreCase("Green"))
        {
            toolbar.setBackgroundColor(Color.GREEN);
            addButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        }
        if(color.equalsIgnoreCase("Yellow"))
        {
            toolbar.setBackgroundColor(Color.YELLOW);
            addButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
            toolbar.setTitleTextColor(Color.BLACK);

        }
        if(color.equalsIgnoreCase("Cyan"))
        {
            toolbar.setBackgroundColor(Color.CYAN);
            addButton.setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
            toolbar.setTitleTextColor(Color.BLACK);
        }
    }

    public void onPause()
    {
        super.onPause();
        StringBuilder out = new StringBuilder();
        for(int i =0; i < ToDoList.size(); i++)
        {
            out.append(ToDoList.get(i));
            out.append("\n");
        }

        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("ToDoList.txt", MODE_PRIVATE));
            outputStreamWriter.write(out.toString());
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            e.getCause();
        }
    }
    public void goToPref(MenuItem item)
    {
        PrefIntent = new Intent(this,MenuPreference.class);
        startActivity(PrefIntent);
    }
}
