package com.example.lukas.fip;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileManager {

    private File file;


    FileManager(Context context, String fileName){
        this.file = new File(context.getFilesDir(), fileName);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }



    //Day management

    String getDaysFromFile(){
        String read = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            read = in.readLine();
            in.close();
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
        return read;
    }

    void writeDaysToFile(String dayListStr){
        try{
            BufferedWriter out = new BufferedWriter( new FileWriter(file));
            out.write(dayListStr);
            out.flush();
            out.close();
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }


    //Date management

    void saveDateList(ArrayList<String> dl){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (String date : dl){
                out.write(date);
                out.newLine();
            }
            out.flush();
            out.close();
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }

    }

    ArrayList<String> loadDateList(){
        ArrayList<String> loadedDateList = new ArrayList<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            String read;
            while ((read = in.readLine()) != null){
                loadedDateList.add(read);
            }
            in.close();
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
        return loadedDateList;
    }
}
