package com.example.pc_adrien.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity
{
    ImageButton addButton;
    RecyclerView rv;
    InputStream fileInput = null;
    List<String> ToDoList = new ArrayList<>();

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
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_cell, parent, false);

            readFileIntoList("ListeDeChoseAFaire.txt");
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            String TodoSting = ToDoList.get(position);
            holder.display(TodoSting);
        }

        @Override
        public int getItemCount()
        {
            return ToDoList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            private final TextView AFaire;

            public MyViewHolder(final View itemView)
            {
                super(itemView);

                AFaire = ((TextView) itemView.findViewById(R.id.TodoTextView));

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


    private void readFileIntoList (String fileName)
    {
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
        readFileIntoList("ListeDeChoseAFaire.txt");
    }
}
