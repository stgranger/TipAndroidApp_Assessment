package sclg2.assessmenttip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangeTipActivity extends Activity
{
    // Declaration of the elements
    private Button saveSettingsButton;
    private EditText tipPercentageEditText;
    // Declaration of the attribut
    private double tipPercentage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
        // call the super constructor
        super.onCreate(savedInstanceState);
        // set the view
		setContentView(R.layout.activity_selecttip);

        // declaration of the elements in the activity (Buttons, TextView, EditText)
		tipPercentageEditText = (EditText)this.findViewById(R.id.nbPeopleEditText);
		saveSettingsButton = (Button)this.findViewById(R.id.saveSettingsButton);

        // Load the preferences tip of percentage
		loadTipPercentage();

        // set the method to the button
		saveSettingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveTip();
			}
		});

	}

    // This method is to load the Tip preference
	private void loadTipPercentage()
    {
		SharedPreferences preferences = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
		tipPercentageEditText.setText(preferences.getString("tipPercentage", "15"));
	}
	
	private Boolean validateTip()
    {
		try
        {
            // try to parse the tip
			Double.parseDouble(tipPercentageEditText.getText().toString());
		}
        catch (NumberFormatException ex)
        {
			// If the validation doesn't work an alert dialog will show
			AlertDialog dialog;
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Enter a correct value")
	    			.setTitle("Incorrect Number")
	    			.setCancelable(false)
	    			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.dismiss();
	    	           }
	    	       });
	    	dialog = builder.create();
	    	dialog.show();
	    	return false;
		}
		return true;
	}

    // This method is to save
	private void saveTip()
    {
        // Control the value for the tip
		if (validateTip())
        {
            // Add the value on the preferences value to be save
			SharedPreferences preferences = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = preferences.edit();
			prefEditor.putString("tipPercentage", tipPercentageEditText.getText().toString());
			prefEditor.commit();
            // Close the activity
			this.finish();
		}
	}

}
