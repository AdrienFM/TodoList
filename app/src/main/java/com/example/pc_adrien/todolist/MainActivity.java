package com.example.pc_adrien.todolist;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.description;

public class MainActivity extends AppCompatActivity
{
    ImageButton addButton;
    RecyclerView rv;
    InputStream fileInput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
            public final List<String> ToDo = Arrays.asList
            (
                "Bonjour","Bonsoir"
             );

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_cell, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            String Todo = ToDo.get(position);
            holder.display(Todo);
        }

        @Override
        public int getItemCount()
        {
            return ToDo.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            private final TextView AFaire;

            public MyViewHolder(final View itemView)
            {
                super(itemView);

                AFaire = ((TextView) itemView.findViewById(R.id.ToDo));

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });

            }

            public void display(String ToDo)
            {
                AFaire.setText(ToDo);
            }
         }
    }

    private void writeToFile (String fileName, String toWrite, int mode)
    {
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName, mode));
            outputStreamWriter.write(toWrite);
            outputStreamWriter.write("\n");
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            e.getCause();
        }
    }

    private void readFromFile (String fileName)
    {
       // String fileContent = "";
        try
        {

            fileInput = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                out.append(line);
            }
           // fileContent = out.toString();
            fileInput.close();
        }
        catch (IOException e)
        {
        }
    }
}
