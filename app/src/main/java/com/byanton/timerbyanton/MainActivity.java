package com.byanton.timerbyanton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_1 = "channel1";
    private TextView txt;
    private EditText ets;
    private Button btn1, btn2,btn3;
    private ProgressBar pb;
    public int a;
    public int b;
    private boolean pause;
    private CountDownTimer mCDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        ets = (EditText) findViewById(R.id.ets);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.btn3);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.txt);


            btn1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String temp = ets.getText().toString();
                    if (ets.getText().toString().equals("")){
                        ets.setError("Write seconds");
                        return;
                    }

                    else if (!ets.equals(temp)) {
                        a = Integer.parseInt(temp);
                    }
                    b = a*1000;
                    pb.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.GONE);
                    btn3.setVisibility(View.VISIBLE);
                  mCDT =  new CountDownTimer(b, 1000) {
                        @Override
                        public void onTick(long l) {
                            ets.setText("" + l / 1000);
                            pb.setProgress((int) (l / 1000));
                        }

                        @Override
                        public void onFinish() {
                            addNotification();
                            pb.setVisibility(View.GONE);

                            txt.setVisibility(View.VISIBLE);
                            txt.setText("Timeout");

                        }
                    }.start();

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pauseTimer();

                    btn1.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.GONE);


                }
            });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                resetTimer();
                btn1.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.GONE);
                ets.setText(null);
                txt.setVisibility(View.GONE);
                txt.setText(null);
                pb.setVisibility(View.GONE);
            }
        });



    }

    private void pauseTimer() {
        mCDT.cancel();
        pause = false;

    }


    private void  resetTimer(){
        if (pause = false){
            ets.setText("");
        }

    }
    private void addNotification(){
        createNotificationChannels();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,CHANNEL_1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Timer by Anton")
                        .setContentText("TIMEOUT")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);


        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             NotificationChannel channel1 = new NotificationChannel(CHANNEL_1,"Channel 1",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }

    }

}
