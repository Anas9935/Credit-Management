package com.example.creditmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class listAdapter extends ArrayAdapter<users> {
private final ArrayList<users> list;
    public listAdapter(Context context, ArrayList<users> lst) {
        super(context, 0,lst);
        list=lst;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view=convertView;
       if(view==null){
           view= LayoutInflater.from(getContext()).inflate(R.layout.todo_users,parent,false);
       }
       TextView name,email,points,sname,id;
       users current=list.get(position);
       name=view.findViewById(R.id.todo_name);
       email=view.findViewById(R.id.todo_email);
       points=view.findViewById(R.id.todo_points);
       sname=view.findViewById(R.id.todo_sname);
       id=view.findViewById(R.id.todo_id);

       name.setText(current.getName());
       email.setText(current.getEmail());
       points.setText(String.valueOf(current.getPoints()));
       String sn=current.getName().substring(0,2);
       sname.setText(sn);
       id.setText(String.valueOf(current.getUid()));

       return view;
    }
}
