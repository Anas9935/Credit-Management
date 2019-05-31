package com.example.creditmanagement.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.creditmanagement.users;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {

    private static final int version =1;
    private static final String DatabaseName="Credit.db";
    private final ArrayList<users> list;

    public SQLHelper( Context context) {
        super(context, DatabaseName, null,version);
        list=new ArrayList<>();
        listAdd();


    }


    private void listAdd(){
        list.add(new users("Sahil","SjSahil@gmail.com",700));
        list.add(new users("Yash","Yashwant@gmail.com",800));
        list.add(new users("Rohan","RohanDon@gmail.com",250));
        list.add(new users("Preeti","PreetyPreeti@gmail.com",650));
        list.add(new users("Ashwarya","iAmAsh123@gmail.com",350));
        list.add(new users("Anas","aaansari@gmail.com",450));
        list.add(new users("Pankaj","PkAhuja@gmail.com",725));
        list.add(new users("Rohit","RohIT2541@gmail.com",852));
        list.add(new users("Eshaan","EshaanKhan@gmail.com",915));
        list.add(new users("Monu","KingMonu@gmail.com",817));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATEUSER = "CREATE TABLE " + ContractClass.entry.userTableName +
                "( " + ContractClass.entry.userId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractClass.entry.userName + " TEXT, " +
                ContractClass.entry.userEmail + " TEXT, " +
                ContractClass.entry.userPoints + " INTEGER)";
        db.execSQL(CREATEUSER);
        String CREATETRANS = "CREATE TABLE " + ContractClass.entry.transactionTableName +
                "( " + ContractClass.entry.transId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractClass.entry.transfrom + " INTEGER, " +
                ContractClass.entry.transTo + " INTEGER, " +
                ContractClass.entry.transTime + " INTEGER," +
                ContractClass.entry.transAmt + " INTEGEER)";
        db.execSQL(CREATETRANS);
        for( int i=0;i<list.size();i++){
            ContentValues value=new ContentValues();
            users current=list.get(i);
            value.put(ContractClass.entry.userName,current.getName());
            value.put(ContractClass.entry.userEmail,current.getEmail());
            value.put(ContractClass.entry.userPoints,current.getPoints());

            db.insert(ContractClass.entry.userTableName,null,value);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String delTransTable = "DROP TABLE IF EXISTS " + ContractClass.entry.transactionTableName;
        db.execSQL(delTransTable);
        String delUserTable = "DROP TABLE IF EXISTS " + ContractClass.entry.userTableName;
        db.execSQL(delUserTable);
        onCreate(db);
    }
}
