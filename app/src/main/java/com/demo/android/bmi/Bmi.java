package com.demo.android.bmi;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class Bmi extends AppCompatActivity {

    AnimationDrawable anim;

    Button button;
    TextView textView1,textView2;
    EditText fieldHeight;
    EditText fieldWeight;

    public static final String PREF = "BMI_PREF";
    public static final String PREF_HEIGHT = "BMI_Height";
    public static final String TAG = "LifeCycle";

    DisplayMetrics monitorsize =new DisplayMetrics();

    private void restorePrefs() {
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        String pref_height = settings.getString(PREF_HEIGHT, "");
        if (!"".equals(pref_height))
        {
            fieldHeight.setText(pref_height);
            fieldWeight.requestFocus();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        settings.edit().putString(PREF_HEIGHT,fieldHeight.getText().toString()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Bmi.onCreate");
        setContentView(R.layout.activity_bmi);
        findViews();
        setListeners();
        restorePrefs();
        playAnimation();
    }

    void findViews(){
        button = (Button) findViewById(R.id.submit);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        fieldHeight = (EditText) findViewById(R.id.height);
        fieldWeight = (EditText) findViewById(R.id.weight);
    }

    void setListeners(){
        button.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Bmi.this,Report.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT",fieldHeight.getText().toString());
            bundle.putString("KEY_WEIGHT",fieldWeight.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    void openOptionsDialog(){
        Toast.makeText(Bmi.this,"顯示Toast訊息",Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog =
                ProgressDialog.show(Bmi.this, "處理中...", "請等一會，處理完畢會自動結束...");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
//                    result.setTextColor(Color.BLUE);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        };
        thread.start();
    }


    void playAnimation(){


        ObjectAnimator tv1 = ObjectAnimator.ofFloat(textView1, "x", monitorsize.widthPixels ,0);
        tv1.setDuration(0);

        tv1.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        ObjectAnimator tv2 = ObjectAnimator.ofFloat(fieldHeight, "x", monitorsize.widthPixels ,0);
        tv2.setDuration(1000);

        ObjectAnimator tv3 = ObjectAnimator.ofFloat(textView2, "x", monitorsize.widthPixels ,0);
        tv3.setDuration(1000);

        ObjectAnimator tv4 = ObjectAnimator.ofFloat(fieldWeight, "x", monitorsize.widthPixels ,0);
        tv4.setDuration(1000);

        ObjectAnimator b1 = ObjectAnimator.ofFloat(button, "x", monitorsize.widthPixels ,0);
        b1.setDuration(1000);

        AnimatorSet as = new AnimatorSet();
        as.playSequentially(tv1,tv2,tv3,tv4,b1);
        as.start();
    }

    void playFrameAnimationFromXML(){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.animator);
        set.setTarget(fieldHeight);
        set.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        textView1.setVisibility(View.INVISIBLE);
//        textView2.setVisibility(View.INVISIBLE);
//        fieldWeight.setVisibility(View.INVISIBLE);
//        fieldHeight.setVisibility(View.INVISIBLE);
        Log.d(TAG,"Bmi.onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Bmi.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Bmi.onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Bmi.onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"Bmi.onRestart");
    }
}
