package com.example.damm03_armazenamentodeinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity{
    protected TextView tvDetNome, tvDetEmail;
    protected Long id;
    protected static int DELETERECORD = 0;
    protected static int BACKBUTTON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetNome = (TextView) findViewById(R.id.tvDetNome);
        tvDetEmail = (TextView) findViewById(R.id.tvDetEmail);

        Intent i = getIntent();
        id = i.getLongExtra("id", 0);
        tvDetNome.setText(i.getStringExtra("nome"));
        tvDetEmail.setText(i.getStringExtra("email"));
    }

    public void btnClickDelete(View v){
        Intent i = new Intent();
        i.putExtra("id", id);

        setResult(DELETERECORD, i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(BACKBUTTON);
        }
        return super.onKeyDown(keyCode, event);
    }
}

