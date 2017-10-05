package com.example.izteeb.PMO;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Izteeb on 9/25/2017.
 */

public class userslotDeleteListAdapter extends ArrayAdapter<userslotDelete> {

    List<userslotDelete> userslotList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    public userslotDeleteListAdapter(@NonNull Context context, @LayoutRes int resource, List<userslotDelete> userslotList) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.userslotList = userslotList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource,null,false);

        TextView userText = (TextView) view.findViewById(R.id.delete_userText);
        TextView slottext = (TextView) view.findViewById(R.id.delete_slotText);
        Button deleteBttn = (Button) view.findViewById(R.id.buttonDelete);

        final userslotDelete usd = userslotList.get(position);

        userText.setText(usd.getUsername());
        slottext.setText(usd.getSlot());

        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeUserSlot(position);

                Toast.makeText(getContext(),"Username: "+((userslotDelete) userslotList.get(position)).getUsername()+" Slot:"+((userslotDelete) userslotList.get(position)).getSlot(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void removeUserSlot(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userslotList.remove(position);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
