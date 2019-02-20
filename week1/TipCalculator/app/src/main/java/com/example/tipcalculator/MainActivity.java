package com.example.tipcalculator;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


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



        setTipValues();
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


}
