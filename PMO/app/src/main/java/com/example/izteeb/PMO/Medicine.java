package com.example.izteeb.PMO;

/**
 * Created by Izteeb on 3/29/2017.
 */

public class Medicine {
    String _medicineName;
    String _dosage;
    String _time;

    public Medicine(){

    }
    public Medicine(String medicineName, String dosage, String time){
        this._medicineName = medicineName;
        this._dosage = dosage;
        this._time= time;
    }
    public String getMed(){
        return this._medicineName;
    }
    public void setMed(String medicineName){
            this._medicineName = medicineName;
    }
    public String getDosage(){
        return this._dosage;
    }
    public void setDosage(String dosage){
            this._dosage = dosage;
    }
    public String getTime(){
        return this._time;
    }
    public void setTime(String time){
        this._time = time;
    }

}
