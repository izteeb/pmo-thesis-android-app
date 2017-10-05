package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Izteeb on 9/25/2017.
 */

public class userslotDeleteAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Map<String,String>> list = new ArrayList<>();
    private Context context;
    String error;

    public userslotDeleteAdapter(ArrayList<Map<String,String>> list, Context context){
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
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_delete_medication, null);
        }

        final TextView slottext = (TextView) view.findViewById(R.id.delete_slotText);
        Button deleteBttn = (Button) view.findViewById(R.id.buttonDelete);
        final TextView userText = (TextView) view.findViewById(R.id.delete_userText);

        userText.setText(list.get(i).get("user"));
        slottext.setText("Slot "+list.get(i).get("slot_number"));


        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure you want to delete this?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String user = userText.getText().toString();
                        String slot = slottext.getText().toString();
                        //Intent intent = new Intent(context, deleteMedicationAdmin.class);
                        //intent.putExtra("userDelete", user);
                        //intent.putExtra("slotDelete",slot);
                       // context.startActivity(new Intent(context, deleteMedicationAdmin.class));
                        new delete_user_slot().execute(user,slot);
                       // Toast.makeText(context,"User: "+user+" Slot: "+slot,Toast.LENGTH_SHORT).show();

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
        });

        return view;
    }

    class delete_user_slot extends AsyncTask<String, Void, String> {

        String delete_med_url;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete_med_url = "http://dmtthesiscpepmo.comli.com/delete_medication.php";

        }



        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);

            String user1,slot1;
            user1 = (String) args[0];
            slot1 = (String) args[1];

            try {
                URL url = new URL(delete_med_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user1,"UTF-8")+"&"+
                        URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode(slot1,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine()) !=null){
                    result +=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();

            }

            return null;


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {

            error = result;
            // g3.setText(result);
            if(error.equals("Error.")){
                Toast.makeText(context,"Error deleting user medication.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Successfully deleted user medication.",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
