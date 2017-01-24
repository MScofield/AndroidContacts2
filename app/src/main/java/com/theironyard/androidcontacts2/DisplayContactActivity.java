package com.theironyard.androidcontacts2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayContactActivity extends AppCompatActivity implements View.OnClickListener {

    EditText newName;
    EditText newNumber;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        Intent intent = getIntent();
        String contact = intent.getStringExtra(MainActivity.SHOW_CONTACT);
        TextView textView = (TextView) findViewById(R.id.ContactView);
        textView.setTextSize(40);
        textView.setText(contact);

        newName = (EditText) findViewById(R.id.editName);
//        System.out.println(newName);
        newName.setText(getIntent().getExtras().getString("newName", ""));
        newNumber = (EditText) findViewById(R.id.editNumber);
//        System.out.println(newNumber);
        newNumber.setText(getIntent().getExtras().getString("newNumber", ""));

        position = getIntent().getExtras().getInt("position", 0);
//        System.out.println(position);

        Button button = (Button) findViewById(R.id.editButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("newName", newName.getText().toString());
        returnIntent.putExtra("newNumber", newNumber.getText().toString());
        returnIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
