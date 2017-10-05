package com.example.izteeb.PMO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class deleteMedicationAdmin extends AppCompatActivity {


    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    ArrayList<Map<String,String>> medicationList;
    userslotDeleteAdapter adapter;
    private ListView listView;
    List<userslotDelete> userList;
    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    public String TAG_MED_OBJECT = "";
    public String TAG_USER_VALUE = "user";
    public String TAG_MIN_VALUE = "min_start";
    public String TAG_HOUR_VALUE = "hour_start";
    public String TAG_AM_PM = "am_pm";
    public String TAG_QUANTITY= "quantity";
    public String TAG_MEDICINE = "medicine_name";
    public String TAG_DOSAGE = "dosage";

    public String TAG_TIME  = "time_start";
    public String TAG_SLOT = "slot_number";
    public String TAG_DESCRIPTION = "description";
    String username;
    String error;

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    @Override
    public void onBackPressed() {

    }

    public void backBttnPressed(){
        Button backBttn = (Button) findViewById(R.id.back_bttn7);

        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(deleteMedicationAdmin.this, Admin_activity.class));
            }
        });
    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            listView.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue!",Toast.LENGTH_SHORT).show();
            listView.setEnabled(false);
        }
    }

    private class LoadMedicineList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(deleteMedicationAdmin.this);
            pDialog.setMessage("Loading medications. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_user_medication);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("medication");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);
                        userList = new ArrayList<>();

                        String user = c.getString(TAG_USER_VALUE);
                        int slot = c.getInt(TAG_SLOT);
                        int min = c.getInt(TAG_MIN_VALUE);
                        int hour = c.getInt(TAG_HOUR_VALUE);
                        String am_pm = c.getString(TAG_AM_PM);
                        String med = c.getString(TAG_MEDICINE);
                        String dosage = c.getString(TAG_DOSAGE);
                        int quantity = c.getInt(TAG_QUANTITY);

                        Map<String, String> map = new TreeMap<>();
                        map.put(TAG_USER_VALUE,user);
                        map.put(TAG_SLOT, Integer.toString(slot));

                       // userList.add(new userslotDelete(slot,user));


                        medicationList.add(map);
                    }

                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pDialog.isShowing())
                pDialog.dismiss();

            adapter = new userslotDeleteAdapter(medicationList, deleteMedicationAdmin.this);
            listView.setAdapter(adapter);
        }
    }

/*
            String[] from={TAG_USER_VALUE,TAG_SLOT};//string array
            int[] to={R.id.delete_userText,R.id.delete_slotText};//int array of views id's

           SimpleAdapter adapter = new SimpleAdapter(deleteMedicationAdmin.this, medicationList,R.layout.row_delete_medication,from,to);
            listView.setAdapter(adapter);

            for(int x= 0 ; x<medicationList.size(); x++){
                Toast.makeText(getApplicationContext(), " "+medicationList.get(x),Toast.LENGTH_LONG).show();
                break;
            }
            */

/*
    public void listOnClicked(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> obj = (HashMap<String, String>) adapter.getItem(i);
                String name = (String) obj.get(TAG_USER_VALUE);

                    Toast.makeText(getApplicationContext(), " "+name,Toast.LENGTH_LONG).show();


            }
        });
    }
*/

    class deleteMedicationBackgroundTask extends AsyncTask<String, Void, String> {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_info_url = "http://dmtthesiscpepmo.comli.com/delete_medication.php";


        }
        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);


            String user2,slot2;
            user2= (String) args[0];
            slot2= (String)args[1];



            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user2,"UTF-8")+"&"+
                        URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode(slot2,"UTF-8");
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            if(result == "Error."){
                Toast.makeText(getApplicationContext(),"Error deleting medication. Please try again to continue.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Medication deleted successfully.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(deleteMedicationAdmin.this, deleteMedicationAdmin.class));
            }


        }


    }


    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_medication_admin);

        medicationList = new ArrayList<Map<String, String>>();
        listView = (ListView) findViewById(R.id.listView2);

        backBttnPressed();
        new LoadMedicineList().execute();
      // listOnClicked();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    try{
                        Thread.sleep(2000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                internetConnection();
                            }
                        });
                    }
                    catch(Exception e){

                    }
                }
            }
        }).start();

    }


    public class userslotDeleteAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Map<String, String>> list = new ArrayList<>();
        Activity context;
        String error;

        public userslotDeleteAdapter(ArrayList<Map<String, String>> list, Activity context) {
            super();
            this.list = list;
            this.context = context;

        }

        @Override
        public boolean isEnabled(int position) {
           return isNetworkAvailable();
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
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_delete_medication, null);
            }

            final TextView slottext = (TextView) view.findViewById(R.id.delete_slotText);
            final Button deleteBttn = (Button) view.findViewById(R.id.buttonDelete);
            final TextView userText = (TextView) view.findViewById(R.id.delete_userText);



            userText.setText(list.get(i).get("user"));
            slottext.setText(list.get(i).get("slot_number"));

            deleteBttn.setEnabled(isNetworkAvailable());
            deleteBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deleteBttn.setTextColor(Color.RED);
                    deleteBttn.setBackgroundResource(R.drawable.button_no_background_red);

                    String user1 = userText.getText().toString();
                    String slot1 = slottext.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(user1+"'s slot"+slot1);
                    builder.setMessage("Are you sure you want to delete "+user1+"'s slot"+slot1+" medication?");
                    builder.setIcon(R.drawable.delete_med);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String user = userText.getText().toString();
                            String slot = slottext.getText().toString();
                            new deleteMedicationBackgroundTask().execute(user,slot);

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
    }
}
