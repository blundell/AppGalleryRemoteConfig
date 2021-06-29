package com.blundell.tut

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.agconnect.remoteconfig.AGConnectConfig
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance

private const val TAG = "TUT"

/**
 * This example shows how AppGallery Connect Remote Configuration
 * can be used to toggle features and/or the UI in your app.
 *
 * This screen shows an EditText where you can modify a UserAttribute on the device.
 * This UserAttribute is the country you have determined the user is from.
 * The UserAttribute has to be setup in AppGallery Connect > Analytics > Management before hand.
 *
 * There is a button where you can refresh the remote config being used,
 * in a 'real' app the cycle between changing attributes & refreshing configs
 * to see changes would not be such a tight loop, but for demo purposes this does the job.
 *
 * SideNote: There is already a "Country/Region" UserAttribute defined in HiAnalytics,
 * that uses the device IP to determine location, perhaps you want to use another rule
 * such as country of birth and so you create a custom UserAttribute.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var config: AGConnectConfig
    private lateinit var analytics: HiAnalyticsInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = AGConnectConfig.getInstance()
        analytics = HiAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        updateUIBasedOnUserAttributes()
        updateUIBasedOnRemoteConfig()
    }

    private fun updateUIBasedOnUserAttributes() {
        val editTextOne: EditText = findViewById(R.id.edit_text_one)

        /**
         * getUserProfiles param: Indicates whether to obtain predefined user attributes.
         * true: Obtains predefined user attributes.
         * false: Obtains custom user attributes.
         */
        val currentCountry = analytics.getUserProfiles(false)["country"] ?: "Spain"
        Log.d(TAG, "Country load: $currentCountry")
        editTextOne.setText(currentCountry)
    }

    private fun updateUIBasedOnRemoteConfig() {
        /**
         * You could use this value to change Activity navigation paths, to swap fragments etc
         * Here, we just change the text.
         */
        val enableFeature = config.getValueAsBoolean("enable_feature_1234_cool")
        val textView: TextView = findViewById(R.id.text_view_one)
        if (enableFeature) {
            textView.text = "Feature Enabled! (Go to the console to toggle this)"
        } else {
            textView.text = "Feature Disabled! (Go to the console to toggle this)"
        }

        /**
         * In the console we have this remote toggle configured to return
         * true or false depening on a custom condition
         * This condition returns 'true' when the UserAttribute 'country' is set to 'UK'
         * And 'false' if UserAttribute 'country' is set to anything else.
         */
        val enableFeature1235 = config.getValueAsBoolean("enable_feature_1235_theme")
        val contentMain: ViewGroup = findViewById(R.id.content_main)
        if (enableFeature1235) {
            contentMain.setBackgroundColor(Color.parseColor("#22FF0000"))
            Toast.makeText(this, "Welcome to the UK!", Toast.LENGTH_SHORT).show()
        } else {
            contentMain.setBackgroundColor(Color.parseColor("#2200FF00"))
            Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onRefreshConfigClick(v: View) {
        updateCountryUserAttribute()
        config.fetch(0) // a value of 0 here is for DEBUGGING ONLY, delete for prod
            .addOnSuccessListener {
                config.apply(it)
                Log.d(TAG, "Applied")
                updateUIBasedOnRemoteConfig()
            }
    }

    private fun updateCountryUserAttribute() {
        val editTextOne: EditText = findViewById(R.id.edit_text_one)
        val currentCountry = editTextOne.text.toString()
        Log.d(TAG, "Country sel: $currentCountry")
        // This sets the custom UserAttribute 'country' we created in the console
        analytics.setUserProfile("country", currentCountry)
    }
}
