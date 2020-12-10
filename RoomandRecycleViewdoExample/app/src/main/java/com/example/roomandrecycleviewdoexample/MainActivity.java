package com.example.roomandrecycleviewdoexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomandrecycleviewdoexample.ROOM.Database;
import com.example.roomandrecycleviewdoexample.ROOM.MyData;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyAdapter myAdapter;
    MyData nowSelectedData;//取得在畫面上顯示中的資料內容


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);//設置資料庫監視


        Button btCreate = findViewById(R.id.button_Create);
        Button btModify = findViewById(R.id.button_Modify);
        Button btClear = findViewById(R.id.button_Clear);
        EditText edName = findViewById(R.id.editText_Name);
        EditText edPhone = findViewById(R.id.editText_Phone);
        EditText edHobby = findViewById(R.id.editText_Hobby);
        EditText edElseInfo = findViewById(R.id.editText_else);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(recyclerView);//設置RecyclerView左滑刪除

        /**=======================================================================================*/
        /**設置修改資料的事件*/
        btModify.setOnClickListener((v) -> {
            new Thread(() -> {
                if(nowSelectedData ==null) return;//如果目前沒前台沒有資料，則以下程序不執行
                String name = edName.getText().toString();
                String phone = edPhone.getText().toString();
                String hobby = edHobby.getText().toString();
                String elseInfo = edElseInfo.getText().toString();
                MyData data = new MyData(nowSelectedData.getId(), name, phone, hobby, elseInfo);
                Database.getInstance(this).getDataUao().updateData(data);
                runOnUiThread(() -> {
                    edName.setText("");
                    edPhone.setText("");
                    edHobby.setText("");
                    edElseInfo.setText("");
                    nowSelectedData = null;
                    myAdapter.refreshView();
                    Toast.makeText(this, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();

        });
        /**=======================================================================================*/
        /**清空資料*/
        btClear.setOnClickListener((v -> {
            edName.setText("");
            edPhone.setText("");
            edHobby.setText("");
            edElseInfo.setText("");
            nowSelectedData = null;
        }));
        /**=======================================================================================*/
        /**新增資料*/
        btCreate.setOnClickListener((v -> {
            new Thread(() -> {
                String name = edName.getText().toString();
                String phone = edPhone.getText().toString();
                String hobby = edHobby.getText().toString();
                String elseInfo = edElseInfo.getText().toString();
                if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                MyData data = new MyData(name, phone, hobby, elseInfo);
                Database.getInstance(this).getDataUao().insertData(data);
                runOnUiThread(() -> {
                    myAdapter.refreshView();
                    edName.setText("");
                    edPhone.setText("");
                    edHobby.setText("");
                    edElseInfo.setText("");
                });
            }).start();
        }));
        /**=======================================================================================*/
        /**初始化RecyclerView*/
        new Thread(() -> {
            List<MyData> data = Database.getInstance(this).getDataUao().displayAll();
            myAdapter = new MyAdapter(this, data);
            runOnUiThread(() -> {
                recyclerView.setAdapter(myAdapter);
                /**===============================================================================*/
                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {//原本的樣貌
                    @Override
                    public void onItemClick(MyData myData) {}
                });
                /**===============================================================================*/
                /**取得被選中的資料，並顯示於畫面*/
                myAdapter.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                    nowSelectedData = myData;
                    edName.setText(myData.getName());
                    edPhone.setText(myData.getOwner());
                    edHobby.setText(myData.getCount());
                    edElseInfo.setText(myData.getElseInfo());
                });
                /**===============================================================================*/
            });
        }).start();
        /**=======================================================================================*/

    }

    private void setRecyclerFunction(RecyclerView recyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {//設置RecyclerView手勢功能
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        myAdapter.deleteData(position);
                        break;

                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}