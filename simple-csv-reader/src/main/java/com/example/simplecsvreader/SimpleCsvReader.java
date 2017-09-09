package com.example.simplecsvreader;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SimpleCsvReader {
    private Context context;
    private String[] splittedLine;
    private TableLayout tableLayout;
    private int colorID,gravityID;

    public boolean isSkipFirstLine() {
        return skipFirstLine;
    }

    private boolean skipFirstLine;

    public void SkipFirstLine(boolean skipFirstLine) {
        this.skipFirstLine = true;
    }

    public void setSkipFirstLine(boolean skipFirstLine) {
        this.skipFirstLine = skipFirstLine;
    }

    public SimpleCsvReader(Context context, TableLayout tableLayout, int columnsQuantity){
        this.context = context;
        this.tableLayout = tableLayout;
        this.splittedLine = new String[columnsQuantity];
        this.colorID = Color.BLACK;
        this.gravityID = Gravity.CENTER;

    }

    public void setCsvTextGravity(int GravityID){
        this.gravityID = gravityID;
    }

    public void setCsvTextColor(int colorID){
        this.colorID = colorID;
    }

    public void generateCSV(File choosenFile){
        if(choosenFile !=null ) {
            String line;
            StringBuilder text = new StringBuilder();
            try {

                BufferedReader br = new BufferedReader(new FileReader(choosenFile));
                while ((line = br.readLine()) != null) {
                    text.setLength(0);
                    text.append(line);

                    String[] split = text.toString()
                            .replaceAll(" ", "")
                            .split(",");

                    for (int i = 0; i < split.length; i++) {

                        splittedLine[i] = split[i];

                    }
                    if (!isSkipFirstLine())
                        addRow();
                    else
                        setSkipFirstLine(false);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            Toast.makeText(context, R.string.pleaseValidFile,Toast.LENGTH_SHORT);
    }

    private void addRow(){

        TableRow row = new TableRow(context);

        for(int i = 0 ; i < splittedLine.length ; i ++){
            TextView textView = new TextView(context);
            textView.setTextColor(colorID);
            textView.setText(splittedLine[i]);
            row.addView(textView);
            TableRow.LayoutParams params = (TableRow.LayoutParams)textView.getLayoutParams();
            params.setMargins(2,2,2,2);
            params.width = TableRow.LayoutParams.WRAP_CONTENT;
            params.height = TableRow.LayoutParams.WRAP_CONTENT;
            params.weight = 1;
            params.gravity = gravityID;
            textView.setLayoutParams(params);
        }

        tableLayout.addView(row,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }


}