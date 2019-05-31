package com.example.creditmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.creditmanagement.Data.ContractClass;
import com.example.creditmanagement.Data.SQLHelper;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {
    private listAdapter adapter;
private ArrayList<users> list;
private int uid;
private SQLHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        ListView allusers = findViewById(R.id.allusersList);

        helper=new SQLHelper(this);
         list=new ArrayList<>();
        getList();

        adapter=new listAdapter(this,list);
        allusers.setAdapter(adapter);

        allusers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uid=list.get(position).getUid();
                Intent intent=new Intent(AllUsersActivity.this,Transaction.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
adapter.notifyDataSetChanged();

    }
    private void getList(){
        SQLiteDatabase db=helper.getReadableDatabase();
        String[] projections=new String[]{ContractClass.entry.userId,ContractClass.entry.userName,ContractClass.entry.userEmail,ContractClass.entry.userPoints};
        Cursor cursor=db.query(ContractClass.entry.userTableName,projections,null,null,null,null,null);
        if(cursor!=null){
            int idIndex=cursor.getColumnIndex(ContractClass.entry.userId);
            int nameIndex=cursor.getColumnIndex(ContractClass.entry.userName);
            int emailIndex=cursor.getColumnIndex(ContractClass.entry.userEmail);
            int pointIndex=cursor.getColumnIndex(ContractClass.entry.userPoints);
                    if(cursor.moveToFirst()){
                        do{
                            int id=cursor.getInt(idIndex);
                            String name=cursor.getString(nameIndex);
                            String email=cursor.getString(emailIndex);
                            int points=cursor.getInt(pointIndex);
                            users newuser=new users(name,email,points);
                            newuser.setUid(id);
                            list.add(newuser);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        adapter=null;
        list=null;
        list=new ArrayList<>();
        getList();
        adapter=new listAdapter(this,list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter=null;
        list=null;
        list=new ArrayList<>();
        getList();
        adapter=new listAdapter(this,list);
    }



}
