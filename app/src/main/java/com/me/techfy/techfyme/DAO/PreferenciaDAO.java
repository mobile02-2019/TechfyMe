package com.me.techfy.techfyme.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.techfy.techfyme.DTO.PreferenciaDTO;
import com.me.techfy.techfyme.adapter.PreferenciaDBHelper;

public class PreferenciaDAO {

    final FirebaseDatabase database;
    DatabaseReference reference;
    volatile PreferenciaDTO preferenciaDTO;
    PreferenciaDBHelper dbLocalHelper;

    public PreferenciaDAO () {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("preferencias");
        preferenciaDTO = new PreferenciaDTO();
    }

    public void salvar (PreferenciaDTO usuarios) {
        DatabaseReference r = reference.child(usuarios.getDatabaseKey());
        r.setValue(usuarios);
    }

    public void carregar (String idUsuario, final FirebasePreferenciaDatabaseCall listener) {
         database.getReference("preferencias/" + idUsuario).limitToFirst(1).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 preferenciaDTO = dataSnapshot.getValue(PreferenciaDTO.class);
                 listener.onDataChange(preferenciaDTO);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onDataCanceled();
             }
         });
    }

    public void gravarNoSQLite (PreferenciaDTO usuarios, Context context) {
        dbLocalHelper = new PreferenciaDBHelper(context);
        SQLiteDatabase dbLocal = dbLocalHelper.getWritableDatabase();
        ContentValues values = new ContentValues(2);
        if(usuarios.getDatabaseKey() == null) return;
        values.put(PreferenciaDBHelper.Preferencia.COLUMN_NAME_USERID, usuarios.getDatabaseKey());
        values.put(PreferenciaDBHelper.Preferencia.COLUMN_NAME_CHECADOS, usuarios.getChecadosAsString());
        dbLocal.insert(PreferenciaDBHelper.Preferencia.TABLE_NAME, null, values);
    }

    public PreferenciaDTO lerDoSQLite(String usuarioId, Context context) {
        dbLocalHelper = new PreferenciaDBHelper(context);
        SQLiteDatabase dbLocal = dbLocalHelper.getReadableDatabase();
        String[] projection = {PreferenciaDBHelper.Preferencia.COLUMN_NAME_CHECADOS};
        String selection = PreferenciaDBHelper.Preferencia.COLUMN_NAME_USERID + " = ?";
        String[] busca = {usuarioId};
        String sortOrder = PreferenciaDBHelper.Preferencia.COLUMN_NAME_CHECADOS + " DESC";

        Cursor cursor = dbLocal.query(PreferenciaDBHelper.Preferencia.TABLE_NAME,
                projection,selection,busca,null,null,sortOrder);

        PreferenciaDTO dto = new PreferenciaDTO();
        dto.setUsuarioId((usuarioId));
        while (cursor.moveToNext()) {
            String listCSVChecada = cursor.getString(
                    cursor.getColumnIndexOrThrow(PreferenciaDBHelper.Preferencia.COLUMN_NAME_CHECADOS)
            );
            for (String opcao : listCSVChecada.split(",")) {
                dto.adicionar(opcao);
            }
        }
        cursor.close();
        return dto;
    }

    public void apagarDoSQLite(PreferenciaDTO usuarios, Context context) {
        dbLocalHelper = new PreferenciaDBHelper(context);
        SQLiteDatabase dbLocal = dbLocalHelper.getWritableDatabase();
        //String[] selectArgs = { usuarios.getDatabaseKey() };
        dbLocal.delete(PreferenciaDBHelper.Preferencia.TABLE_NAME, PreferenciaDBHelper.Preferencia.COLUMN_NAME_USERID, null);

    }

}