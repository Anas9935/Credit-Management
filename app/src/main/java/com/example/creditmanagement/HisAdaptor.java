package com.example.creditmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.creditmanagement.Data.ContractClass;
import com.example.creditmanagement.Data.SQLHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class HisAdaptor extends ArrayAdapter<TransObject> {
private final ArrayList<TransObject> list;
private final SQLHelper helper;
    public HisAdaptor(Context context, ArrayList<TransObject> lst) {
        super(context, 0,lst);
        helper=new SQLHelper(getContext());
        list=lst;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.todo_history,parent,false);
        }
        TextView from,to,time,amt;
        from=view.findViewById(R.id.hisFromName);
        to=view.findViewById(R.id.hisToname);
        time=view.findViewById(R.id.hisdate);
        amt=view.findViewById(R.id.hisAmt);

        TransObject current=list.get(position);

        from.setText(name(current.getTransFrom()));
        to.setText(name(current.getTransTo()));
        time.setText(getDate(current.getTransTime()));
        amt.setText(String.valueOf(current.getTraansamt()));
        return view;
    }
    private String getDate(long timestamp){
        DateFormat format=new SimpleDateFormat("dd/mm/yyyy");
        return format.format(new Date(timestamp*1000));
    }
    private String name(int id){
        SQLiteDatabase db=helper.getReadableDatabase();
        String name=null;
        String[] projections=new String[]{ContractClass.entry.userName};
        String selection =ContractClass.entry.userId+" = ?";
        String[] selectonArgs=new String[]{String.valueOf(id)};
        Cursor cursor=db.query(ContractClass.entry.userTableName,projections,selection,selectonArgs,null,null,null);
        if(cursor!=null){
            int nameIndex=cursor.getColumnIndex(ContractClass.entry.userName);
            if(cursor.moveToFirst()){
                do{
                    name= cursor.getString(nameIndex);
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
        if(name==null){
            return "UNknown";
        }else
            return name;
    }
}
