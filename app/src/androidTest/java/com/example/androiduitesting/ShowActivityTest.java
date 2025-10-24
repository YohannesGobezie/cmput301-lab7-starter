package com.example.androiduitesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

public class ShowActivityTest {
    @Test
    public void testActivitySwitch() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra(ShowActivity.CITY_NAME_KEY, "Edmonton");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            assertNotNull("ShowActivity should not be null", activity);
            assertFalse("ShowActivity should not be finishing", activity.isFinishing());
        });

        scenario.close();
    }

    @Test
    public void testCityNameConsistency() {
        String testCityName = "Vancouver";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra(ShowActivity.CITY_NAME_KEY, testCityName);

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            TextView cityNameTextView = activity.findViewById(R.id.textView_cityName);
            assertNotNull("City name TextView should not be null", cityNameTextView);

            String displayedCityName = cityNameTextView.getText().toString();
            assertEquals("Displayed city name should match the city that was clicked", testCityName, displayedCityName);
        });

        scenario.close();
    }

    @Test
    public void testBackButton() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra(ShowActivity.CITY_NAME_KEY, "Geneva");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            Button backButton = activity.findViewById(R.id.button_back);
            assertNotNull("Back button should not be null", backButton);
            assertEquals("Back button should not be visible", Button.VISIBLE, backButton.getVisibility());
            assertTrue("Back button should be enabled", backButton.isEnabled());

            backButton.performClick();
        });

        scenario.onActivity(activity -> {
            assertTrue("Activity should be finishing after back button click", activity.isFinishing());
        });

        scenario.close();
    }
}
