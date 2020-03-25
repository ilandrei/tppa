package com.example.CarStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.CarStore.models.Car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private List<Car> carList;
    public ListView listView;
    public int myInt;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            this.myInt = savedInstanceState.getInt("myInt");
            Log.d("myInt", String.valueOf(this.myInt));
        }

        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Autohaus");

        carList = this.getCars();
        listView = (ListView) findViewById(R.id.listView);

        List<String> namesList = null;
        namesList = carList.stream().map(Car::getName).collect(Collectors.toList());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);

                Car selectedCar = carList.get(position);

                intent.putExtra("name", selectedCar.getName());
                intent.putExtra("description", selectedCar.getDescription());
                intent.putExtra("img", selectedCar.getImg());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("On start called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("On resume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("On pause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("On stop called");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("On restart called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("On destroy called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        System.out.println("Reached");
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logOut:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Log out");
                alertDialogBuilder.setMessage("Are you sure you want to log out?");
                alertDialogBuilder.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Login.class);

                        startActivity(intent);

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
                System.out.println("Shown");
                return true;
            case R.id.contact:
                intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER,"0760998118");

                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"Choose contact app"));

                return true;
            case R.id.settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.writeToast:
                String timeStamp = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                writeFile(MainActivity.this,"toast",timeStamp);
                return true;
            case R.id.readToast:
                String result = readFile(MainActivity.this,"toast");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        this.myInt = 1;
        savedInstanceState.putInt("myInt", this.myInt);
    }

    private List<Car> getCars()
    {
        List<Car> list = new ArrayList<Car>();
        list.add(new Car("Ford", R.drawable.focus, "Ford Fiesta"));
        list.add(new Car("Volkswagen", R.drawable.arteon, "Volkswagen Arteon"));

        return list;
    }
    public void writeFile(Context mcoContext, String fileName, String sBody){

        try{
            File newFile = new File(mcoContext.getFilesDir(),fileName);
            FileWriter writer = new FileWriter(newFile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readFile(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

}
