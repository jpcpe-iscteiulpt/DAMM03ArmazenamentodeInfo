package com.example.damm03_armazenamentodeinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.damm03_armazenamentodeinfo.models.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoDataSource {
    protected SQLiteDatabase db;
    protected DatabaseHelper dbHelper;

    public ContactoDataSource(Context c){
        dbHelper = new DatabaseHelper(c);
    }

    //Abrir base de dados
    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    //Fechar base de dados
    public void close(){
        db.close();
    }

    //Criar novo contacto
    public Contacto create(String nome, String email){
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("email", email);

        long lastId = db.insert("contactos", null, values);

        return new Contacto(lastId, nome, email);
    }

    //Obter contacto da base de dados
    public Contacto get(long id){
        Cursor c = db.rawQuery("SELECT * FROM contactos WHERE id=" + id, null);

        if(c.getCount() == 0){
            return null;
        }else{
            c.moveToFirst();
            Contacto contacto = new Contacto(id, c.getString(1), c.getString(2));
            c.close();
            return contacto;
        }
    }

    //Obter todos os contactos da tabela de contactos
    public List<Contacto> getAll(){
        Cursor c = db.rawQuery("SELECT * FROM contactos", null);

        if (c.getCount() ==0){
            return null;
        }else{
            c.moveToFirst();
            List<Contacto> contactList =  new ArrayList<Contacto>();

            while(!c.isAfterLast()){
                contactList.add(new Contacto(c.getLong(0),c.getString(1),c.getString(2)));
                c.moveToNext();
            }
            c.close();
            return contactList;
        }
    }

    //apagar contacto da tabela
    public void apagar(long id){
        db.delete("contactos", "id", new String[]{String.valueOf(id)});
    }

    //contar registos
    public int count(){
        Cursor c = db.rawQuery("SELECT * FROM contactos",null);
        return c.getCount();
    }
}
