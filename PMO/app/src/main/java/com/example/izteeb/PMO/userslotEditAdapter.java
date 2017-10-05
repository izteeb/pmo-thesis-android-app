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
 * Created by Izteeb on 9/25/2017.
 */

public class userslotEditAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Map<String,String>> list = new ArrayList<>();
    private Context context;

    public userslotEditAdapter(ArrayList<Map<String,String>> list, Context context){
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
            view = inflater.inflate(R.layout.row_editmedication, null);
        }

        final TextView slottext = (TextView) view.findViewById(R.id.row_editSlot);
        Button deleteBttn = (Button) view.findViewById(R.id.buttonEdit);
        final TextView userText = (TextView) view.findViewById(R.id.row_editUsername);

        userText.setText(list.get(i).get("user"));
        slottext.setText(list.get(i).get("slot_number"));


        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userText.getText().toString();
                String slot = slottext.getText().toString();

                Intent intent = new Intent(context, deleteMedicationAdmin.class);
                intent.putExtra("userEdit", user);
                intent.putExtra("slotEdit", slot);
                context.startActivity(intent);

                notifyDataSetChanged();
            }
        });

        return view;
    }



}
