package com.portfolio.hectorpinedatodo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button buttonAdd;
    EditText editItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.addButton);
        editItem = findViewById(R.id.textInputEditText);
        rvItems = findViewById(R.id.listStuff);

        //editItem.setText("I'm doing this from java");

        loadItems();
       /*
        items = new ArrayList<>();
        items.add("Buy");
        items.add("Go");
        items.add("fun");
        */

       ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
           @Override
           public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
               Toast.makeText(getApplicationContext(),"Item was deleted",Toast.LENGTH_SHORT).show();
               saveItems();
           }
       };

       itemsAdapter =new ItemsAdapter(items, onLongClickListener);
       rvItems.setAdapter(itemsAdapter);
       rvItems.setLayoutManager(new LinearLayoutManager(this));

       buttonAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String todoItem = editItem.getText().toString();
               items.add(todoItem);
               itemsAdapter.notifyItemInserted(items.size()-1);
               editItem.setText("");
               Toast.makeText(getApplicationContext(),"Item added",Toast.LENGTH_SHORT).show();
               saveItems();
           }
       });

    }
    private File getDataFile() {
        return new File(getFilesDir(),"data.txt");
    }
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();
        }
    }
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items",e);
        }

    }

}
