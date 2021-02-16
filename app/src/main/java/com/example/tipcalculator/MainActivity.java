package com.example.tipcalculator;

import android.support.annotation.LongDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.NumberFormat;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    public static final String TAG = "MainActivity";
    //declare variables for the widgets
    private EditText editTextBillAmount;
    private SeekBar seekBar;

    private TextView textViewTipAmount;
    private TextView textViewSeekBarPercent;
    private TextView textViewTotalAmount;

    //declare the variables for the calculations
    private double billAmount = 0;
    private double percent = 0;
    private double tip = 0;


    //set the number formats to be used for the $ amounts and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add Listeners to Widgets

        //listener for the edit text view
        editTextBillAmount = (EditText)findViewById(R.id.editText_EnterAmount);
        editTextBillAmount.addTextChangedListener((TextWatcher)this);

        //listener for tip text view
        textViewTipAmount = (TextView)findViewById(R.id.textView_tipAmount);

        //textViewTipAmount.setText(seekbarprogress);

        textViewSeekBarPercent = (TextView)findViewById(R.id.textView_seekBarLabel);


        //listener for total amount text view
        textViewTotalAmount = (TextView)findViewById(R.id.textView_totalAmount);


        //listener for seekbar
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //String p = percentFormat.format(progress);
                percent = ((double)progress) / 100;
                textViewSeekBarPercent.setText(Integer.toString(progress)+"%");
                calculate();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

         /* Note:   int i, int i1, and int i2
       represent start, before, count respectively
       The charSequence is converted to a String and parsed to a double for you  */
    }

    public void afterTextChanged(CharSequence charSequence, int i, int i1, int i2){

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
        try {


            Log.d(TAG, "inside onTextChanged method: charSequence=" + charSequence);
            //surround risky calculations with try catch (what if billAmount is 0?
            //charSequence is converted to a String and parsed to a double for you
            billAmount = Float.parseFloat(charSequence.toString());

            //billAmount = Double.parseDouble(charSequence.toString()) / 100;
            //textViewTotalAmount.setText(NumberFormat.getNumberInstance().format(billAmount));

            //if ()
            Log.d(TAG, "Bill Amount=" + billAmount);

            //setText on the textView
            //textViewTotalAmount.setText(currencyFormat.format(billAmount));

            //perform tip and total calculation and update UI by calling calculate
        } catch(NumberFormatException e) { // stops the app from crashing if there's an empty string in the editText field
            billAmount = 0;
            tip = 0;
            //textViewTipAmount.setText(currencyFormat.format(0));
            //textViewTotalAmount.setText(currencyFormat.format(0));
        }
        calculate();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //calculate and display tip and total amounts
    private void calculate(){
        Log.d(TAG, "inside calculate method");


       // format percent and display in percentTextView
      //textViewTipAmount.setText(percentFormat.format(percent));

       // calculate the tip and total

         tip = (billAmount * percent);

        Log.d("Tip", String.valueOf(tip));
        textViewTipAmount.setText(currencyFormat.format(tip));
//
//      //use the tip example to do the same for the Total
        double total = billAmount + tip;
        textViewTotalAmount.setText(currencyFormat.format(total));

       // display tip and total formatted as currency
       //user currencyFormat instead of percentFormat to set the textViewTip
       // tipTextView.setText(currencyFormat.format(tip));
       //use the tip example to do the same for the Total
    }

}