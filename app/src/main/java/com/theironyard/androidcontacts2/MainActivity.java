package com.theironyard.androidcontacts2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    static final String SHOW_CONTACT = "com.theironyard.androidcontacts.contact";
    static final int CONTACT_REQUEST = 1;
    final String FILENAME = "contact.csv";
    //    defining variable types
    ArrayAdapter<String> contacts;
    ListView list;
    EditText name;
    EditText number;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//          connecting view ID's to variables
        list = (ListView) findViewById(R.id.list);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        addButton = (Button) findViewById(R.id.addButton);
//          creating the listview
        contacts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        loadContact();
        list.setAdapter(contacts);
//          setting up listeners for user input
        addButton.setOnClickListener(this);
        list.setOnItemLongClickListener(this);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nam  = name.getText().toString();
        String num = number.getText().toString();
        contacts.add(nam + "  ( " + num + " )");
        name.setText("");
        number.setText("");
        saveContact();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String contact = contacts.getItem(position);
        contacts.remove(contact);
        saveContact();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, DisplayContactActivity.class);
        String contact = contacts.getItem(position);
        intent.putExtra(SHOW_CONTACT, contact);
        intent.putExtra("position", position);
        startActivityForResult(intent, CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_REQUEST) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                System.out.println(position);
                String newNam = data.getStringExtra("newName");
                System.out.println(newNam);
                String newNum = data.getStringExtra("newNumber");
                System.out.println(newNum);
                String contact = contacts.getItem(position);
                System.out.println(contact);
                contacts.remove(contact);
                contacts.add(newNam + "  ( " + newNum + "  )");
                saveContact();
            }
        }
    }

    private void saveContact(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i<contacts.getCount();i++) {
                String contact = contacts.getItem(i);
                sb.append(contact + "\n");
            }
            fos.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContact() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while(br.ready()) {
                String contactLine = br.readLine();
                contacts.add(contactLine);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}