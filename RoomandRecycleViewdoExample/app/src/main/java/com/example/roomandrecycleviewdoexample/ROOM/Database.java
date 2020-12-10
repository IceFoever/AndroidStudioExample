package com.example.roomandrecycleviewdoexample.ROOM;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {MyData.class},version = 1,exportSchema = true)
public abstract class Database extends RoomDatabase {
    public static final String DB_NAME= "Dataf.db" ;
    private static volatile Database instance ;

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance ;
    }

    private static Database create(final Context context) {
        return Room.databaseBuilder(context, Database.class, DB_NAME).build();
    }
    public abstract DataUao getDataUao();


}