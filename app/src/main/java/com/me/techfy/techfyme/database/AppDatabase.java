package com.me.techfy.techfyme.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.me.techfy.techfyme.DAO.NoticiaDao;
import com.me.techfy.techfyme.modelo.NoticiaDb;

@Database(entities = {NoticiaDb.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoticiaDao noticiaDao();
}