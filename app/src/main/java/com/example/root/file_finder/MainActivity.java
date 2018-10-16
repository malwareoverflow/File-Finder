package com.example.root.file_finder;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Environment;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button searchBtn;
    String[] download = {};
    int TotalDirectories = 0;
    List<String> File = new ArrayList<String>();
    List<String> Directories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toast.makeText(getApplicationContext(),"There are total "+ getalldirectories().length +" directories", Toast.LENGTH_SHORT).show();
        getfilerecursive();
        Toast.makeText(getApplicationContext(),"total files are "+ File.size() +" total directories are "+TotalDirectories, Toast.LENGTH_LONG).show();

        download=   setautocomplete();

        Toast.makeText(getApplicationContext(),"root is "+ Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, download);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void getfilerecursive()

    {

        AddDir( getDirbypath(Environment.getExternalStorageDirectory().getPath()));
        AddFiles(getFilebypath(Environment.getExternalStorageDirectory().getPath()));

        Recursive(Directories);

    }



    public boolean AddFiles(String[] Files)
    {
        try
        {
            for (String item : Files)
            {

                File.add(item);

            }
            return false;
        }
        catch (Exception ex)
        {
          ///  GC.Collect();
          //  GC.WaitForPendingFinalizers();
            return false;
        }

    }


    public void AddDir(String[] Dir)
    {
        try
        {
            for(String item : Dir)
            {
                TotalDirectories += 1;

                Directories.add(item);
            }
        }
        catch (Exception ex)
        {
         //   GC.Collect();
           // GC.WaitForPendingFinalizers();

        }

    }
    public boolean IsDirectoryEmpty(String path)
    {
        try
        {

            File directory = new File(path);
            File[] contents = directory.listFiles();
// the directory file is not really a directory..
            if (contents == null) {

            }
// Folder is empty
            else if (contents.length == 0) {
return true;
            }
// Folder contains files
            else {
return false;
            }


        }
        catch (Exception ex)
        {
          //  GC.Collect();
          //  GC.WaitForPendingFinalizers();

        }
        return true;
    }

    public void Recursive(List<String> Dirs)
    {
        boolean FileFound = false;
        try
        {

            for (int x = 0; x < Dirs.size(); x++)
            {
                if (IsDirectoryEmpty(Directories.get(x))==false)
                {

                    AddDir(getDirbypath(Directories.get(x)));


                    if (AddFiles( getFilebypath(Directories.get(x))))
                    {
                        FileFound = true;
                        break;
                    }
                    else
                    {
                        AddFiles(getFilebypath(Directories.get(x)));
                    }



                }
                Directories.remove(x);
            }
            if (Directories.size() > 0 && FileFound==false)
            {
                Recursive(Directories);
            }
        }
        catch (Exception ex)
        {

           // GC.Collect();
            //GC.WaitForPendingFinalizers();
        }

    }

    public void getfilesrecursively(){

        try{

            AddDir(getalldirectoriesfromroot());
            AddFiles(getallfilesfromroot());

            Recursive(Directories);

        }
        catch (Exception ex){


        }
    }

public String[] getDirbypath(String path){
    List<String> dir = new ArrayList<String>();
        try{

            File root = new File( path);

            File[] directories = root.listFiles();
            for (File inFile : directories) {
                if (inFile.isDirectory()) {
                    dir.add(inFile.getPath());
                }
            }
        }

        catch (Exception ex){


        }
    String[] alldir = new String[dir.size()];
    alldir = dir.toArray(alldir);
        return  alldir;
}


    public String[] getFilebypath(String path){
        List<String> files = new ArrayList<String>();
        try{

            File root = new File( path);

            File[] directories = root.listFiles();
            for (File inFile : directories) {
                if (inFile.isFile()) {
                    files.add(inFile.getPath());
                }
            }
        }

        catch (Exception ex){


        }
        String[] allfiles = new String[files.size()];
        allfiles = files.toArray(allfiles);
        return  allfiles;
    }


    public String[] getallfilesfromroot()
    {
        List<String> file = new ArrayList<String>();
        try{

            File root = Environment.getExternalStorageDirectory();

            File[] directories = root.listFiles();
            for (File inFile : directories) {
                if (inFile.isFile()) {
                    file.add(inFile.getName());
                }
            }

        }
        catch (Exception ex){


        }
        String[] allfiles = new String[file.size()];
        allfiles = file.toArray(allfiles);
        return  allfiles;

    }


    public String[] getalldirectoriesfromroot()
    {
        List<String> dir = new ArrayList<String>();
    try{

        File root = Environment.getExternalStorageDirectory();

        File[] directories = root.listFiles();
        for (File inFile : directories) {
            if (inFile.isDirectory()) {
                dir.add(inFile.getName());
            }
        }

    }
        catch (Exception ex){


        }
        String[] alldirectories = new String[dir.size()];
        alldirectories = dir.toArray(alldirectories);
        return  alldirectories;

    }



    public String[] setautocomplete(){

        try {

 // String path =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

         //   File directory = new File(path);
         //   File[] files = directory.listFiles();
          //  List<String> downloadfiles = new ArrayList<String>();




         //   for (int i = 0; i < files.length; i++)
        //    {

             //   downloadfiles.add(files[i].getName());

       //    }
            String[] allfiles = new String[File.size()];
            allfiles = File.toArray(allfiles);
          return  allfiles;
        }
        catch (Exception ex){
            String[] Months = new String[] { "January", "February",
                    "March", "April", "May", "June", "July", "August", "September",
                    "October", "November", "December" };
            return Months;

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
