package com.nickdown.ysera;

import java.util.Calendar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {
	
	public Button bt_calc;
	public EditText et_day;
	public EditText et_month;
	public EditText et_year;
	public TextView tv_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt_calc = (Button)findViewById(R.id.btncalc);
		et_day = (EditText)findViewById(R.id.et_day);
		et_month = (EditText)findViewById(R.id.et_month);
		et_year = (EditText)findViewById(R.id.et_year);
		tv_result = (TextView)findViewById(R.id.tv_result);

		if(bt_calc == null){
			Log.e("ERRRROOR", "Button is null");
		}
		
		bt_calc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				updateCountdown();
			}
			
		});
				
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	private void updateCountdown(){
		String day = et_day.getText().toString();
		int d = Integer.parseInt(day);
		
		String month = et_month.getText().toString();
		int m = Integer.parseInt(month);
		
		String year = et_year.getText().toString();
		int y = Integer.parseInt(year);
		
		Calendar cal = Calendar.getInstance();
		cal.set(y, m, d);
		
		long eventTime = cal.getTimeInMillis();

		Calendar today = Calendar.getInstance();
		long todayInMillis = today.getTimeInMillis();
		
		long diff = eventTime - todayInMillis;
		
		int secs = (int) (diff/1000);
		int mins = (secs/60);
		int hours = (mins/60);
		int days = (hours/24);
		int weeks = (days/7);
		
		tv_result.setText("Days: " + days + ", Hours: " + hours + ", Minutes: " + mins + ", Seconds: " + secs);
	
	}
	
	

}
