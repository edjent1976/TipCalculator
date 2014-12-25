package com.example.dougstipcalculator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



public class DougsTipCalculator extends Activity {
    //constants used when saving/restoring state
    private static final String BILL_TOTAL ="BILL TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal; //bill amount entered by the user
    private int currentCustomPercent; // tip % set with the SeekBar
    private EditText tip10EditText; // displays 10% tip
    private EditText total10EditText; //displays total with 10% tip
    private EditText tip15EditText; // displays 15% tip
    private EditText total15EditText; // displays total with 15% tip
    private EditText tip20EditText; // displays 20% tip
    private EditText total20EditText; // displays total with 20% tip
    private EditText billEditText; // accepts user input for bill total
    private TextView customTipTextView; //displays custom tip percentage
    private EditText tipCustomEditText; //displays custom tip amount
    private EditText totalCustomEditText; // displays total with custom tip

    //Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// call superclass's version;
        setContentView(R.layout.activity_main); // inflate the GUI

        //check if app just started or is being restored from memory
        if (savedInstanceState == null) // the app just started running
        {
            currentBillTotal = 0.0; // initialize the bill amount to zero
            currentCustomPercent = 18; // initialize the custom tip to 18%;
        } //end if
        else //app is being restored from memory, not executed from scratch
        {
            //initialize the bill amount to the saved amount
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);

            //initialize the custom tip to saved tip percent
            currentCustomPercent =
                    savedInstanceState.getInt(CUSTOM_PERCENT);
        }//end else

        //get references to the 10%, 15% and 20% tip and total EditTexts
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditView);
        tip20EditText = (EditText) findViewById(R.id.tip20editText);

        total20EditText = (EditText) findViewById(R.id.total20TextView);

        //get tje TextView displaying the custom tip percentage
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);

        //get the custom tip and total EditTexts
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

        //get the BillEditText
        billEditText = (EditText) findViewById(R.id.billEditText);

        //billEditTextWatcher handles billEditText;s on TextChanged event
        billEditText.addTextChangedListener(billEditTextWatcher);

        //get the SeekBar used to set the custom tip amount
        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
}//end method onCreate

        // upsates 10, 15, and 20 percent tipEditTexts
        private void updateStandard()
{
        //calculate bill total with a ten percent tip
        double tenPercentTip = currentBillTotal *.1;
        double tenPercentTotal = currentBillTotal + tenPercentTip;

        //set tipTenEditText's text to tenPercentTip
        tip10EditText.setText(String.format("%.02f", tenPercentTip));

        //set totalTenEditText's to tenPercentTip
        total10EditText.setText(String.format("%.02f", tenPercentTotal));

       //calculate bill total with a 15 percent tip
       double fifteenPercentTip = currentBillTotal *.15;
       double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

       //set tipFifteenEdit's text to fifteenPercentTip
       tip15EditText.setText(String.format("%.02f", fifteenPercentTip));

       //set totalfifteenEditText's to fifteenPercentTotal
       total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

       //calculate bill total with a twenty percent tip
       double twentyPercentTip = currentBillTotal *.20;
       double twentyPercentTotal = currentBillTotal +twentyPercentTip;

       //set tipTwentyEditText's to twentyPercentTip
       tip20EditText.setText(String.format("%.02f", twentyPercentTip));

      //set totaltwentyEditText's to twentyPercentTotal
      total20EditText.setText(String.format("%.02f", twentyPercentTotal));

} //end method updateStandard

  //updates the custom tip and total Edit Texts
   private void updateCustom()
   {
       //set custom TipTextView's text to match the position of the SeekBar
       customTipTextView.setText(currentCustomPercent + "%");

       //calculate the custom tip amount
       double customTipAmount = currentBillTotal + currentCustomPercent * .01;

       //calculate the total bill, including the custom tip
       double customTotalAmount = currentBillTotal + customTipAmount;

       //display the tip and total bill amounts
       tipCustomEditText.setText(String.format("%.02f", customTipAmount));

       totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
   } //end method updateCustom

    //save values of billEditText and customSeekBar
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putDouble(BILL_TOTAL, currentBillTotal);
        outState.putInt(CUSTOM_PERCENT,currentCustomPercent);
    } //end method onSaveInstanceState

    //called when the user changes the position of the SeekBar
    private OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener() {
                //update currentCustomPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    //sets current CustomPercent to position of the SeekBar's thumb
                    currentCustomPercent = seekBar.getProgress();
                    updateCustom(); //update EditTexts for custom tip and total
            } //end methood on ProgressChanged
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
                //end method onStartTrackingTouch

             @Override
            public void onStopTrackingTouch (SeekBar seekBar) {}
                //end method onStopTrackingTouch
            }; // end onSeekBarChangeListener
      //event handling object that responds to billEditText's events
      private TextWatcher billEditTextWatcher = new TextWatcher() {
          //called when the user enter's a number
          @Override
          public void onTextChanged (CharSequence s, int start, int before, int count)
          {
           //convert billEditText's text to a double
             try
             {
                 currentBillTotal = Double.parseDouble(s.toString());
             }//end try

              catch (NumberFormatException nfe)
              {
                currentBillTotal = 0.00; // default if an exception occurs
              }//end catch

              //update the standard and custom tip EditTexts
              updateStandard(); //update the 10, 15, and 20% EditTexts
              updateCustom(); // update the custom tip EditTexts
          }// end method on TextChanged

          @Override
          public void afterTextChanged(Editable s) {}
          //end method after text changed
          @Override
          public void beforeTextChanged(CharSequence s , int start, int count, int after)
          {} // end method beforeTextChanged
      };// end billeEditTextWatcher
} // end class DougsTipCalculator



