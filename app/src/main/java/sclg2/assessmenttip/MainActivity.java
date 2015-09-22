package sclg2.assessmenttip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
    // Declaration of the elements
    private EditText billAmountEditText;
    private Button changeTipButton;
    private TextView tipPctView;
    private EditText nbOfPpleView;
    private Button calcTipButton;
    private TextView tipAmtView;
    private TextView totalAmtView;
    private TextView totalAmtByPersonView;
    // Declaration of the attributs
    private double tipPercentage;
    private double billAmount;
    private double totalAmountByPerson;
    private double tipAmount;
    private double totalAmount;
    private int numberPeople;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
        // call the super constructor
		super.onCreate(savedInstanceState);
        // set the view
		setContentView(R.layout.activity_main);

        // declaration of the elements in the activity (Buttons, TextView, EditText)
        billAmountEditText = (EditText)this.findViewById(R.id.billAmtEditText);
        changeTipButton = (Button)this.findViewById(R.id.changeTipButton);
        tipPctView = (TextView)this.findViewById(R.id.tipPctView);
        nbOfPpleView = (EditText)this.findViewById(R.id.nbOfPpleView);
        calcTipButton = (Button)this.findViewById(R.id.calcTipButton);
        tipAmtView = (TextView)this.findViewById(R.id.tipAmtView);
        totalAmtView = (TextView)this.findViewById(R.id.totalAmtView);
        totalAmtByPersonView = (TextView)this.findViewById(R.id.totalAmtByPersonView);

        // set the methods to the buttons
        calcTipButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
            {
				calculateTip();
			}
		});
        changeTipButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startChangeTipActivity();
            }
        });

        // set a text by default to the edit text nbOfPpleView
        nbOfPpleView.setText(String.format("%d", 1));
	}

	@Override
	public void onStart()
    {
        // call the super constructor
        super.onStart();
        // Load the preferences tip of percentage
		loadTipPercentage();
	}

	@Override
	public void onResume()
    {
        // call the super constructor
        super.onResume();
        // Call the calculateTip method
		calculateTip();
	}

	private void hideKeyboard()
    {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	}

    // This method calculates the tip when the user click on the button
    // or after to have change the tip in preferences)
	private void calculateTip()
    {
		try
        {
			billAmount = Double.parseDouble(billAmountEditText.getText().toString());
			hideKeyboard();
            loadTipPercentage();
		}
        catch (NumberFormatException ex)
        {
			billAmount = 0;
		}
        try
        {
            numberPeople = Integer.parseInt(nbOfPpleView.getText().toString());
            hideKeyboard();
            loadTipPercentage();
        }
        catch (NumberFormatException ex)
        {
            numberPeople = 1;
            nbOfPpleView.setText(String.format("%d", 1));
        }
		
		tipAmount = billAmount * tipPercentage;
		totalAmount = billAmount + tipAmount;
        if (numberPeople > 0)
        {
            totalAmountByPerson = totalAmount / numberPeople;
            totalAmountByPerson = Math.ceil(totalAmountByPerson);
        }
        else
        {
            totalAmountByPerson = totalAmount;
            totalAmountByPerson = Math.ceil(totalAmountByPerson);
        }

        tipAmtView.setText(String.format("$%.2f", tipAmount));
        totalAmtView.setText(String.format("$%.2f", totalAmount));
        totalAmtByPersonView.setText(String.format("$%.0f", totalAmountByPerson));
	}

    // This method is to load the Tip preference
	private void loadTipPercentage()
    {
		SharedPreferences preferences = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
		String tipPctString = preferences.getString("tipPercentage", "15");
        tipPctView.setText(String.format("%s%%", tipPctString));
		tipPercentage = Double.parseDouble(tipPctString) / 100;
	}

    // this method is to start the other activity to set a new tip
	private void startChangeTipActivity()
    {
		Intent settingsIntent = new Intent();
		settingsIntent.setClass(this, ChangeTipActivity.class);
		startActivity(settingsIntent);		
	}
}
