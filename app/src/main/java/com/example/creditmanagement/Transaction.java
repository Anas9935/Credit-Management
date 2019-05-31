package com.example.creditmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creditmanagement.Data.ContractClass;
import com.example.creditmanagement.Data.SQLHelper;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {
    private EditText amt;
private TextView textView;
    private ListView lv;
private int uid;
    private ArrayList<users> list;
private int myPt;

private SQLHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Button sendBtn = findViewById(R.id.trans_sendBtn);
        amt=findViewById(R.id.trans_amt);
        lv=findViewById(R.id.trans_listView);
        textView=findViewById(R.id.transText);
        TextView name = findViewById(R.id.trans_name);
        TextView email = findViewById(R.id.trans_email);
        TextView point = findViewById(R.id.trans_points);

        Intent intent=getIntent();
        uid=intent.getIntExtra("uid",-1);

        helper=new SQLHelper(this);

        list=new ArrayList<>();
        //populate List
        getList();
        for( int i=0;i<list.size();i++){
            if(list.get(i).getUid()==uid){
                myPt=list.get(i).getPoints();
                name.setText(list.get(i).getName());
                email.setText(list.get(i).getEmail());
                point.setText(String.valueOf(list.get(i).getPoints()));
                list.remove(list.get(i));
            }
        }

        listAdapter adapter = new listAdapter(this, list);
        lv.setAdapter(adapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amt.getText()!=null){
                    textView.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(Transaction.this,"Please add the valid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                send(position);
                finish();
                startActivity(
                        new Intent(Transaction.this,AllUsersActivity.class));
            }
        });

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
    private void send(int pos){
        SQLiteDatabase db=helper.getWritableDatabase();
        //do the transaction and store the result
        String name=list.get(pos).getName();
        //update the point of uid --
        int amount=Integer.valueOf(amt.getText().toString());
        int myNewPt=myPt-amount;
        String updateMe="UPDATE "+ContractClass.entry.userTableName+" SET "+ContractClass.entry.userPoints+"="+myNewPt+" WHERE uid="+uid;
        db.execSQL(updateMe);


        //update the point of list.get(pos).getUid() ++
        int hisNewPt=list.get(pos).getPoints()+amount;
        String updateHim="UPDATE "+ContractClass.entry.userTableName+" SET "+ContractClass.entry.userPoints+"="+hisNewPt+" WHERE uid="+list.get(pos).getUid();db.execSQL(updateHim);
        Toast.makeText(this,"Amount Is being Credited in account of "+name,Toast.LENGTH_LONG).show();

        //add the data in the transactions
        long time=System.currentTimeMillis()/1000;
        ContentValues values=new ContentValues();
        values.put(ContractClass.entry.transfrom,uid);
        values.put(ContractClass.entry.transTo,list.get(pos).getUid());
        values.put(ContractClass.entry.transTime,time);
        values.put(ContractClass.entry.transAmt,Integer.valueOf(amt.getText().toString()));
        db.insert(ContractClass.entry.transactionTableName,null,values);
    }
}
