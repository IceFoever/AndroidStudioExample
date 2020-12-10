package com.example.roomandrecycleviewdoexample.ROOM;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataUao {
    String tablename = "MyData";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(MyData myData);

    @Query("INSERT INTO "+tablename+"(name,owner,count,elseInfo) VALUES(:name, :owner,:count, :elseData)")
    void insertData(String name, String owner, int count, String elseData);

    @Query("SELECT * FROM " + tablename)
    List<MyData> displayAll();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tablename +" WHERE name = :name")
    List<MyData> findDataByName(String name);

    @Update
    void updateData(MyData myData);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE "+tablename+" SET name = :name, owner= :owner, count= :count, elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id,String name,String owner,int count,String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(MyData myData);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tablename + " WHERE id = :id")
    void deleteData(int id);

}
