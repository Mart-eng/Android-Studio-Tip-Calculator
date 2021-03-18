package com.example.tipcalculator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {

    public static final String TAG = "MainActivity";
    //declare variables for the widgets
    private EditText editTextBillAmount;
    private SeekBar seekBar;

    private TextView textViewTipAmount;
    private TextView textViewSeekBarPercent;
    private TextView textViewTotalAmount;

    //new variables
    private TextView textViewSplitAmount;
    private Spinner spinner;
    private String spinnerLabel = "";
    ArrayAdapter<CharSequence> adapter;

    //declare the variables for the calculations
    private double billAmount = 0;
    private double total = 0;
    private double percent = 0;
    private double tip = 0;

    private double split = 0;
    private boolean tip_checked = false;
    private boolean total_checked = false;
    private static final int MY_PERMISSION_REQUEST = 0;
    private static final String[] PERMISSIONS_REQUIRED = {
            Manifest.permission.SEND_SMS
    };


    //set the number formats to be used for the $ amounts and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////new listeners/////////////////////////

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.tip_split_array, android.R.layout.simple_spinner_item);
        spinner.getBackground();

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }
        textViewSplitAmount = findViewById(R.id.textView_split);
        textViewSplitAmount.setText(spinner.getSelectedItem().toString());


        //////////////////////////////////////////
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
        if (tip_checked == true){
            tip = Math.ceil(tip);
        }

//      //use the tip example to do the same for the Total
        total = billAmount + tip;


        if (total_checked == true){
            total = Math.ceil(total);
        }



        textViewTotalAmount.setText(currencyFormat.format(total));
        textViewTipAmount.setText(currencyFormat.format(tip));
       // display tip and total formatted as currency
       //user currencyFormat instead of percentFormat to set the textViewTip
       // tipTextView.setText(currencyFormat.format(tip));
       //use the tip example to do the same for the Total
        ///////////// throwing things in here to update
        String var = (spinner.getSelectedItem().toString());
        double amtOfppl = Double.parseDouble(var);
        split = total / amtOfppl;
        textViewSplitAmount.setText(currencyFormat.format(split));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String var = (spinner.getSelectedItem().toString());
        double amtOfppl = Double.parseDouble(var);
        split = total / amtOfppl;
        textViewSplitAmount.setText(currencyFormat.format(split));
        onRadioButtonClicked(view);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()){
            case R.id.rb_no_rounding:
                Log.d(TAG,"Everything stays the same.");
                tip_checked = false;
                total_checked = false;
                break;

            case R.id.rb_tip_only_rounding:
                tip_checked = true;
                total_checked = false;
                calculate();
                break;

            case R.id.rb_total_only_rounding:
                total_checked = true;
                tip_checked = false;
                calculate();
                break;
        }
        calculate();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_setting:
                share();
                Toast.makeText(this,"Share clicked", Toast.LENGTH_LONG).show();
                break;

            case R.id.info_setting:
                showInfoFragmentDiaglog();
                Toast.makeText(this,"info Clicked", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showInfoFragmentDiaglog(){
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.show(getFragmentManager(),"tag");
    }

    private void share(){
        String textToSend = "The bill is " + currencyFormat.format(billAmount) + " with " + currencyFormat.format(tip) + " tip. The total" +
                " is " + currencyFormat.format(total) + " split between " + spinner.getSelectedItem() + " is " +
                currencyFormat.format(split) + " per person.";

        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Pick an app")
                .setText(textToSend)
                .startChooser();
    }

    private boolean checkPermission() {
        boolean permissionGranted = true;
        for (String permission : PERMISSIONS_REQUIRED) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionGranted = false;
                ActivityCompat.requestPermissions(this, new String[]{permission}, MY_PERMISSION_REQUEST);
            }
        }
        return permissionGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    share();

                }
        }
    }

}