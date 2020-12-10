package com.example.roomandrecycleviewdoexample.ROOM;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyData")
public class MyData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name ;
    private String owner;
    private String count ;
    private String elseInfo;

    public MyData(String name, String owner, String count, String elseInfo){
        this.name = name;
        this.owner = owner;
        this.count = count;
        this.elseInfo = elseInfo ;
    }
    @Ignore
    public MyData(int id,String name, String owner, String count, String elseInfo){
        this.id = id ;
        this.name = name;
        this.owner = owner;
        this.count = count;
        this.elseInfo = elseInfo ;
    }

    public int getId(){
        return id ;
    }

    public void setId(int id){
        this.id = id ;
    }

    public String getName(){
        return  name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getOwner(){
        return owner ;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getCount(){
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getElseInfo() {
        return elseInfo;
    }

    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
