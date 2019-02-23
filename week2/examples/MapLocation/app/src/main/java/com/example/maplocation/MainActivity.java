package com.example.maplocation;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends LifecycleLoggingActivity {


    /**
     * Debugging tag used by the Android logger.
     */
    private String TAG = getClass().getSimpleName();

    /**
     * Holds a reference to the EditText where the user adds the
     * address.
     */
    private EditText mEditTextAdress;





    private Button mMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapButton = findViewById(R.id.map_button);
        mEditTextAdress = findViewById(R.id.address_edit_text);



    }

    /**
     * Called by the Android Activity framework after the user adds an
     * address to map.
     *
     * @param view The view.
     */
    public void mapAddress(View view) {
        // Used to reveal or hide the EditText as required.
            startMap();
    }

    /**
     * Start the appropriate Activity to map the address.
     */
    private void startMap() {
        try {
            // Get the address entered by the user.
            String address = mEditTextAdress.getText().toString();

            // Launch the activity by sending an intent.  Android will
            // choose the right one or let the user choose if more
            // than one Activity can handle it.

            // Create an Intent that will launch the "Maps" app.
            final Intent geoIntent = makeMapsIntent(address);

            // Check to see if the Maps app exists to handle the "geo"
            // intent.
            if (geoIntent.resolveActivity(getPackageManager()) != null)
                // Start the Maps app.
                startActivity(geoIntent);
            else
                // Start the Browser app.
                startActivity(makeBrowserIntent(address));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Factory method that returns an Intent that designates the "Map"
     * app.
     */
    private Intent makeMapsIntent(String address) {
        // Note the "loose coupling" between the Intent and the app(s)
        // that handle this Intent.
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="
                        + Uri.encode(address)));
    }

    /**
     * Factory method that returns an Intent that designates the
     * "Browser" app.
     */
    private Intent makeBrowserIntent(String address) {
        // Note the "loose coupling" between the Intent and the app(s)
        // that handle this Intent.

        // Create the intent.
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://maps.google.com/?q="
                        + Uri.encode(address)));

        // WebView Browser Tester on Emulators without Google APIs will
        // not remain open unless the activity is started as a new task.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }
}
