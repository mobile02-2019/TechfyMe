package com.me.techfy.techfyme.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.me.techfy.techfyme.modelo.NoticiaDb;

import java.util.List;

@Dao
public interface NoticiaDao {
    @Insert
    void inserirNoticia(NoticiaDb noticiaDb);

    @Update
    void atualizarNoticia(NoticiaDb noticiaDb);

    @Delete
    void deletarNoticia(NoticiaDb noticiaDb);

    @Query("SELECT * FROM updateNoticia WHERE TituloNoticia LIKE (:titulo)")
    public NoticiaDb getNoticiaByTitulo(String titulo);

//    @Query("SELECT * FROM updateNoticia ORDER BY dataDbNoticia DESC")
//    LiveData<List<NoticiaDb>> todasNoticias();


}