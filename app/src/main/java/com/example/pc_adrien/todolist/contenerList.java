package com.example.pc_adrien.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by PC_Adrien on 2017-05-03.
 */

public class contenerList extends Activity
{
    Button backButton;
    Button doneButton;
    TextView TitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contener_list);

        final Intent MyIntentBack =  new Intent(this,MainActivity.class);

        backButton = (Button) findViewById(R.id.back_button);
        doneButton = (Button) findViewById(R.id.done_button);
        TitleTextView = (TextView) findViewById(R.id.title_editText);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(MyIntentBack);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String input = TitleTextView.getText().toString();
                if(input.isEmpty())
                {
                    new AlertDialog.Builder(doneButton.getContext())
                            .setTitle("Erreur")
                            .setMessage("L'entrée du texte est vide")
                            .show();
                }
                else
                {
                    writeToFile("ToDoList.txt",input,MODE_APPEND);
                    startActivity(MyIntentBack);
                }
            }
        });
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
}
