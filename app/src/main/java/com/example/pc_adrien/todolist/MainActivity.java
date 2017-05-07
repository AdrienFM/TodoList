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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ImageButton addButton;
    RecyclerView rv;

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
    }

    rv = (RecyclerView) findViewById(R.id.list);
    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setAdapter(new MyAdapter());

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
        private final List<Pair<String,String>> characters = Arrays.asList(
                Pair.create("test", "test")
        );


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contener_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        Pair<String, String> pair = characaters.get(position);
        holder.display(pair);
    }

    @Override
    public int getItemCount()
    {
        return characters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView name;
        private final TextView description;

        private Pair<String, String> currentPair;

        public MyViewHolder(final View itemView)
        {
            super(itemView);

            name = ((TextView) itemView.findViewById(R.id.title_editText));
            description = ((TextView) itemView.findViewById(R.id.contener_editText));

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(currentPair.first)
                            .setMessage(currentPair.second)
                            .show();
                }
            });

        }

        public void display(Pair<String, String> pair)
        {
            currentPair = pair;
            name.setText(pair.first);
            description.setText(pair.second);
        }
    }

}
