package com.ghifa.mobile.kelompokproyek.component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by acer on 22/03/16.
 */
public class Hari {

    public static String getNamaHari(String tanggal){
        try {
            String expectedPattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
            String[] days = new String[] {
                "Minggu",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jum'at",
                "Sabtu",
            };

            Calendar calendar = Calendar.getInstance();
            Date date = formatter.parse(tanggal);
            calendar.setTime(date);

            int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            return days[dayWeek];

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            return "";
        }


    }

    public static String getNamaBulan(String tanggal){
        try {
            String expectedPattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
            String[] days = new String[] {
                    "Januari",
                    "Februari",
                    "Maret",
                    "April",
                    "Mei",
                    "Juni",
                    "Juli",
                    "Agustus",
                    "September",
                    "Oktober",
                    "November",
                    "Desember"
            };

            Calendar calendar = Calendar.getInstance();
            Date date = formatter.parse(tanggal);
            calendar.setTime(date);

            int dayWeek = calendar.get(Calendar.MONTH);

            return days[dayWeek];

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            return "";
        }


    }

}
