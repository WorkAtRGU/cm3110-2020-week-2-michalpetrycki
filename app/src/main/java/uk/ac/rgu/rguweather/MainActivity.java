package uk.ac.rgu.rguweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add on click listener to the get forecast button
        Button btnGetForecast = findViewById(R.id.btn_get_forecast);
        btnGetForecast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_forecast){
            // get the weather forecast
        }
    }
}