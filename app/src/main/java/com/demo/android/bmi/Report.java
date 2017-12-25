package com.demo.android.bmi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Report extends AppCompatActivity implements View.OnClickListener {

    TextView result;
    TextView suggest;
    Button back;
    public static final String TAG = "LifeCycle";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Report.onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Report.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Report.onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Report.onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Report.onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"Report.onRestart");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Report.onCreate");
        setContentView(R.layout.activity_report);
        findViews();
        calcBMI();
    }

    void findViews() {
        result = (TextView) findViewById(R.id.result);
        suggest = (TextView) findViewById(R.id.suggest);
        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
    }

    void calcBMI() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String height = bundle.getString("KEY_HEIGHT");
        String weight = bundle.getString("KEY_WEIGHT");
        double h = Double.parseDouble(height) / 100;
        double w = Double.parseDouble(weight);
        double BMI = w / (h * h);
        Log.d(TAG, "BMI=" + BMI);
        System.out.println("BMI = " + BMI);

        DecimalFormat df = new DecimalFormat("0.00");

        result.setText("您的BMI值：" + df.format(BMI));

        if (BMI > 25) {
            suggest.setText(R.string.advice_heavy);
            showNotification(BMI);}
        else if (BMI < 20)
            suggest.setText(R.string.advice_light);
        else
            suggest.setText(R.string.advice_average);

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    protected void showNotification (double BMI) {
        NotificationManager barManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification barMsg = new Notification.Builder(this).setContentTitle("你的BMI值過高").setContentText("通知監督人").setSmallIcon(R.drawable.snorlax).build();
        barMsg.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ios_notification);
        barManager.notify(0,barMsg);
    }
}
