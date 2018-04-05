package com.example.damm03_armazenamentodeinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.damm03_armazenamentodeinfo.models.Contacto;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected EditText etNome, etEmail;
    protected ListView lvContactos;
    protected ContactoDataSource ds;
    protected HashMap<Integer, Long> hm;
    protected static int DETAILS = 0;
    protected static int DELETERECORD = 0;
    protected static int BACKBUTTON = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        lvContactos = (ListView) findViewById(R.id.lvContactos);

        ds = new ContactoDataSource(this);
        ds.open();

        hm = new HashMap<Integer, Long>();

        populateListView();

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                Context ctx = getApplicationContext();
                Intent i = new Intent(ctx, DetailActivity.class);

                long currPos = hm.get(position);
                Contacto c = ds.get(currPos);

                i.putExtra("id", c.getId());
                i.putExtra("nome", c.getNome());
                i.putExtra("email", c.getEmail());

                //Neste pôr 0 em vez de definir DETAILS era a mesma coisa
                startActivityForResult(i, DETAILS);

            }
        });
    }


    public void btnGuardarContacto(View v){
        if(etNome.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
        }else{
            ds.create(etNome.getText().toString(), etEmail.getText().toString());
            etNome.setText("");
            etEmail.setText("");

            etNome.requestFocus();
            Toast.makeText(this, "Contacto introduzido", Toast.LENGTH_SHORT).show();

            populateListView();
        }
    }

    public void populateListView(){
        int c = ds.count();
        String[] values = new String[c];
        if(c > 0){
            List<Contacto> lc = ds.getAll();

            int i = 0;
            for(Contacto cont: lc){
                values[i] = cont.getNome();
                hm.put(i, cont.getId());
                i++;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lvContactos.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == DELETERECORD){
            ds.apagar(data.getLongExtra("id", 0));
            populateListView();
            Toast.makeText(this, "Registo apagado.", Toast.LENGTH_LONG).show();
        }else if(resultCode == BACKBUTTON){
            populateListView();
        }
    }
}
