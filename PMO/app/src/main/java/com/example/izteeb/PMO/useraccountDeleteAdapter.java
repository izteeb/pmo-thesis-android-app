package com.example.izteeb.PMO;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Izteeb on 9/26/2017.
 */

public class useraccountDeleteAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Map<String,String>> list = new ArrayList<>();
    private Context context;

    public useraccountDeleteAdapter(ArrayList<Map<String,String>> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_delete_user, null);
        }

        TextView accounttext = (TextView) view.findViewById(R.id.row_deleteAccount);
        Button deleteBttn = (Button) view.findViewById(R.id.buttonDeleteUser);
        final TextView userText = (TextView) view.findViewById(R.id.row_deleteUser);

        userText.setText(list.get(i).get("username"));
        accounttext.setText(list.get(i).get("role"));


        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userText.getText().toString();
                Intent intent = new Intent(context, deleteUserAdmin.class);
                intent.putExtra("userDelete", user);
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });

        return view;
    }


}
