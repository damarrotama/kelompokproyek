package com.ghifa.mobile.kelompokproyek.component;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCustomTimer {
    Color color;

    public MyCustomTimer() {
        color = new Color();
    }

    public void setTimer(final TextView tv, long time, final String tag, final LinearLayout bgTimer, final String bgtag) {

        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                if (tv.getTag().toString().equals(tag)) {
                    long seconds = millisUntilFinished / 1000;
                    tv.setTextColor(color.parseColor("#ffffff"));
                    tv.setText("Batas waktu pembayaran : " + String.format("%02d:%02d:%02d", seconds / 3600,(seconds % 3600) / 60, (seconds % 60)));
                }
                if (bgTimer.getTag().toString().equals(bgtag)) {
                    bgTimer.setBackgroundColor(color.parseColor("#45abd8"));
                }
            }

            public void onFinish() {
                if (tv.getTag().toString().equals(tag)) {
                    tv.setTextColor(color.parseColor("#ffffff"));
                    //tv.setText("Waktu untuk konfirmasi pembayaran anda telah habis, silahkan ulangi pembayaran.");
                    tv.setText("Gagal.");
                }
                if (bgTimer.getTag().toString().equals(bgtag)) {
                    bgTimer.setBackgroundColor(color.parseColor("#ff0004"));
                }
            }

        }.start();

    }

    public void setTimer2(final TextView tv, long time) {

        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                tv.setTextColor(color.parseColor("#03c900"));
                tv.setText(String.format("%02d:%02d:%02d", seconds / 3600,
                        (seconds % 3600) / 60, (seconds % 60)));
            }

            public void onFinish() {
                tv.setTextColor(color.parseColor("#ff0004"));
                tv.setText("Gagal.");
            }

        }.start();

    }

}