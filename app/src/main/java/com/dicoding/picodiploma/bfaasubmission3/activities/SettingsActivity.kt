package com.dicoding.picodiploma.bfaasubmission3.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.picodiploma.bfaasubmission3.AlarmReceiver
import com.dicoding.picodiploma.bfaasubmission3.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Settings"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var alarmReceiver: AlarmReceiver
        private lateinit var alarmSwitchPreference: SwitchPreferenceCompat

        companion object {
            private const val REMINDER_ALARM_KEY = "reminder"
        }
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            alarmReceiver = AlarmReceiver()

            alarmSwitchPreference = findPreference<SwitchPreferenceCompat>(REMINDER_ALARM_KEY) as SwitchPreferenceCompat
            alarmSwitchPreference.isChecked = preferenceManager.sharedPreferences.getBoolean(REMINDER_ALARM_KEY, false)
        }
        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String? ) {
            if(key == REMINDER_ALARM_KEY) {
                if(sharedPreferences != null) {
                    alarmSwitchPreference.isChecked = sharedPreferences.getBoolean(REMINDER_ALARM_KEY, false)
                }
            }
            setAlarm(alarmSwitchPreference.isChecked)
        }

        private fun setAlarm(state: Boolean) {
            if(state)
                context?.let { alarmReceiver.setRepeatingAlarm(it) }
            else
                context?.let { alarmReceiver.cancelAlarm(it) }
        }
    }


}