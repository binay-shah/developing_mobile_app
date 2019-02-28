package com.example.tipcalculator;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG =
            "DEBUG";


    EditText etBillAmount;

    TextView tvTipPercent;

    TextView tvTipAmount;

    TextView tvBillTotalAmount;

    float percentage = 0;
    float tipTotal = 0;
    float finalBillAmount = 0;
    float totalBillAmount = 0;

    final float REGULAR_TIP_PERCENTAGE = 10;
    final float DEFAULT_TIP_PERCENTAGE = 15;
    final float EXCELLENT_TIP_PERCENTAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBillAmount = (EditText)findViewById(R.id.etBillAmount);
        tvTipPercent = (TextView)findViewById(R.id.tvTipPercent);
        tvTipAmount  = (TextView)findViewById(R.id.tvTipAmount);
        tvBillTotalAmount = (TextView)findViewById(R.id.tvBillTotalAmount);

        setTipValues();
        Log.d(TAG, "created -- percentage =  "+percentage);
        if(savedInstanceState != null){
            percentage = savedInstanceState.getFloat("percentage");
            Log.d(TAG, "recreated -- percentage=  "+percentage);
        }

        etBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateFinalBill();
                setTipValues();
            }
        });




    }

    @SuppressLint("StringFormatMatches")
    private void setTipValues() {
        tvTipPercent.setText(getString(R.string.main_msg_tippercent, percentage));
        tvTipAmount.setText(getString(R.string.main_msg_tiptotal, tipTotal));
        tvBillTotalAmount.setText(getString(R.string.main_msg_billtotalresult, finalBillAmount));
    }

    public void setTipPercent(View view){
        switch (view.getId()){
            case R.id.ibRegularService:
                percentage = REGULAR_TIP_PERCENTAGE;
                break;
            case R.id.ibGoodService:
                percentage = DEFAULT_TIP_PERCENTAGE;
                break;
            case R.id.ibExcellentService:
                percentage = EXCELLENT_TIP_PERCENTAGE;
                break;
        }

        calculateFinalBill();
        setTipValues();
    }

    private void calculateFinalBill() {

        if (percentage == 0)
            percentage = DEFAULT_TIP_PERCENTAGE;

        if(!etBillAmount.getText().toString().equals("") && !etBillAmount.getText().toString().equals("."))
            totalBillAmount = Float.valueOf(etBillAmount.getText().toString());
        else
            totalBillAmount = 0;

        tipTotal = (totalBillAmount*percentage)/100;
        finalBillAmount = totalBillAmount + tipTotal;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("percentage", percentage);
        Log.d(TAG, "onSaveInstanceState() -- percentage =  "+percentage);



    }


    @Override
    protected void onStart() {
        // Always call super class for necessary
        // initialization/implementation.
        super.onStart();
        Log.d(TAG, "onStart() - the activity is about to become visible");
    }

    /**
     * Hook method called after onRestoreStateInstance(Bundle) only if
     * there is a prior saved instance state in Bundle
     * object. onResume() is called immediately after
     * onStart(). onResume() is called when user resumes activity from
     * paused state (onPause()) User can begin interacting with
     * activity. Place to start animations, acquire exclusive
     * resources, such as the camera.
     */
    @Override
    protected void onResume() {
        // Always call super class for necessary
        // initialization/implementation and then log which lifecycle
        // hook method is being called.
        super.onResume();
        Log.d(TAG,
                "onResume() - the activity has become visible (it is now " +
                        "\"resumed\")");
    }

    /**
     * Hook method called when an Activity loses focus but is still visible in
     * background. May be followed by onStop() or onResume(). Delegate more CPU
     * intensive operation to onStop for seamless transition to next activity.
     * Save persistent state (onSaveInstanceState()) in case app is killed.
     * Often used to release exclusive resources.
     */
    @Override
    protected void onPause() {
        // Always call super class for necessary
        // initialization/implementation and then log which lifecycle
        // hook method is being called.
        super.onPause();
        Log.d(TAG,
                "onPause() - another activity is taking focus (this activity " +
                        "is about to be \"paused\")");
    }

    /**
     * Called when Activity is no longer visible. Release resources
     * that may cause memory leak. Save instance state
     * (onSaveInstanceState()) in case activity is killed.
     */
    @Override
    protected void onStop() {
        // Always call super class for necessary
        // initialization/implementation and then log which lifecycle
        // hook method is being called.
        super.onStop();
        Log.d(TAG,
                "onStop() - the activity is no longer visible (it is now " +
                        "\"stopped\")");
    }

    /**
     * Hook method called when user restarts a stopped activity. Is
     * followed by a call to onStart() and onResume().
     */
    @Override
    protected void onRestart() {
        // Always call super class for necessary
        // initialization/implementation and then log which lifecycle
        // hook method is being called.
        super.onRestart();
        Log.d(TAG, "onRestart() - the activity is about to be restarted()");
    }

    /**
     * Hook method that gives a final chance to release resources and
     * stop spawned threads. onDestroy() may not always be called-when
     * system kills hosting process
     */
    @Override
    protected void onDestroy() {
        // Always call super class for necessary
        // initialization/implementation and then log which lifecycle
        // hook method is being called.
        super.onDestroy();
        Log.d(TAG, "onDestroy() - the activity is about to be destroyed");
    }
}
