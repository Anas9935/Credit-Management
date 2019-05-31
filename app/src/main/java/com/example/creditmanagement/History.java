package com.example.creditmanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.creditmanagement.Data.ContractClass;
import com.example.creditmanagement.Data.SQLHelper;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    private ArrayList<TransObject> list;

    private SQLHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView tv = findViewById(R.id.history_lv);
        helper=new SQLHelper(this);

        list=new ArrayList<>();
        getItems();
        HisAdaptor adaptor = new HisAdaptor(this, list);
        tv.setAdapter(adaptor);
    }
    private void getItems(){
        SQLiteDatabase db=helper.getReadableDatabase();
        String[] projections=new String[]{ContractClass.entry.transId,ContractClass.entry.transfrom,ContractClass.entry.transTo,ContractClass.entry.transTime,ContractClass.entry.transAmt};
        Cursor cursor=db.query(ContractClass.entry.transactionTableName,projections,null,null,null,null,null);
        if(cursor!=null){
            int idIndex=cursor.getColumnIndex(ContractClass.entry.transId);
            int nameIndex=cursor.getColumnIndex(ContractClass.entry.transfrom);
            int toIndex=cursor.getColumnIndex(ContractClass.entry.transTo);
            int timeIndex=cursor.getColumnIndex(ContractClass.entry.transTime);
            int amtIndex=cursor.getColumnIndex(ContractClass.entry.transAmt);
            if(cursor.moveToFirst()){
                do{
                    int id=cursor.getInt(idIndex);
                    int name=cursor.getInt(nameIndex);
                    int to=cursor.getInt(toIndex);
                    int time=cursor.getInt(timeIndex);
                    int amt=cursor.getInt(amtIndex);
                    TransObject object=new TransObject(name,to,time,amt);
                    object.setTid();
                    list.add(object);
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
    }
}
