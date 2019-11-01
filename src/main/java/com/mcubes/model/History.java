package com.mcubes.model;

/**
 * Created by A.A.MAMUN on 9/17/2019.
 */
public class History
{
    private String drname;
    private String date;
    private String body;

    public History(String drname, String date, String body){
        this.drname = drname;
        this.date = date;
        this.body = body;
    }

    @Override
    public String toString() {
        return "History{" +
                "drname='" + drname + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}



