package uk.ac.rgu.rguweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import uk.ac.rgu.rguweather.data.ForecastRepository;
import uk.ac.rgu.rguweather.data.LocationForecast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Private variables */
    // App context variable - needed for using ForecastRepository
    private Context context;
    // ForecastRepository variable - will call and return LocationForecast instance.
    private ForecastRepository forecastRepository;
    // LocationForecast variable - it will generate random weather object.
    private LocationForecast locationForecast;
    // Variable checkbox for bookmark.
    private CheckBox chk_bookmark;
    // Variable for spinner used for selecting number of future days.
    private Spinner sp_number_of_days;
    // Variable for holding spinner state - it's initialized twice - during onCreate method and during
    // user selection.
    private boolean spinnerInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get application context
        context = getApplicationContext();

        // Add on click listener to the get forecast button
        Button btnGetForecast = findViewById(R.id.btn_get_forecast);
        btnGetForecast.setOnClickListener(this);

        // Initialize checkbox.
        initializeBookmarkCheckbox();

        // Initialize spinner for future days selection.
        initializeSpinner();

    }

    @Override
    public void onClick(View view) {

        // If 'Get Forecast' button has been clicked.
        if (view.getId() == R.id.btn_get_forecast){
            // get the weather forecast

            updateViews(0);

        }

    }

    // Initialize checkbox for location bookmark and add event listener to it.
    private void initializeBookmarkCheckbox(){

        // Get checkbox from View.
        chk_bookmark = findViewById(R.id.chk_bookmark);

        // Add event listener. It is called every time when user checks bookmark checkbox.
        chk_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                // Get location entered by user.
                String location = ((TextView) findViewById(R.id.et_location)).getText().toString();

                // If checkbox has been checked.
                if(isChecked){

                    // Simply print output to the console.
                    Log.d("TAG", "User has favoured the location " + location);

                }

                // If checkbox has been unchecked.
                else{

                    // Simply print output to the console.
                    Log.d("TAG", "User has removed " + location + " from their favourites.");

                }

            }

        });

    }

    // Add event listener to spinner.
    private void initializeSpinner(){

        // Get spinner from view.
        sp_number_of_days = findViewById(R.id.sp_number_of_days);

        // Set event listener on spinner.
        sp_number_of_days.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){

                // Called only when spinner is initialized when onCreate function is called.
                if(!spinnerInitialized){

                    // Set spinner flag.
                    spinnerInitialized = true;

                    // Escape from function/
                    return;
                }

                // Get number of future days selected by user.
                // As spinner items are strings it needs to be converted into int.
                int itemSelected = Integer.parseInt(parentView.getItemAtPosition(position).toString());

                // Update views.
                updateViews(itemSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){}

        });

    }

    // Update views for current day - withoout user selection.
    private void updateViews(){
        updateViews(0);
    }

    // Updates all views.
    private void updateViews(int numberOfDays){

        //Initialize forecast repository object using current application context.
        forecastRepository = ForecastRepository.getRepository(getApplicationContext());

        // Get location entered by user.
        String location = ((TextView) findViewById(R.id.et_location)).getText().toString();

        // Create locationForecast object using forecastRepository and passing location
        // and number of days selected by user.
        if(numberOfDays > 0)
            locationForecast = forecastRepository.getForecastFor(location, numberOfDays);
        // With just location entered.
        else
            locationForecast = forecastRepository.getForecastFor(location);

        // Get all TextViews which has to be updated.
        TextView tv_location_forecast = findViewById(R.id.tv_location_forecast);
        TextView tv_date = findViewById(R.id.tv_date);
        TextView tv_max_temp_val = findViewById(R.id.tv_max_temp_val);
        TextView tv_min_temp_val = findViewById(R.id.tv_min_temp_val);
        TextView tv_weather_text = findViewById(R.id.tv_weather_text);

        // Updated texts in desired TextViews objects.
        tv_location_forecast.setText(getString(R.string.tv_location_forecast, locationForecast.getLocationName()));
        tv_date.setText(getString(R.string.tv_date, locationForecast.getDate()));
        tv_max_temp_val.setText(getString(R.string.tv_max_temp_val, locationForecast.getMaxTemp(), " \u2103"));
        tv_min_temp_val.setText(getString(R.string.tv_min_temp_val, locationForecast.getMinTemp(), " \u2103"));
        tv_weather_text.setText(locationForecast.getWeather());

    }

}
