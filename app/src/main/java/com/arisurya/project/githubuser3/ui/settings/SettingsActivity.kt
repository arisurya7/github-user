package com.arisurya.project.githubuser3.ui.settings


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.arisurya.project.githubuser3.R
import com.arisurya.project.githubuser3.data.model.RemainderModel
import com.arisurya.project.githubuser3.databinding.ActivitySettingsBinding
import com.arisurya.project.githubuser3.preference.AlarmPreference
import com.arisurya.project.githubuser3.receiver.AlarmReceiver

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder: RemainderModel
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customActionBarSetting()

        val remainderPreference = AlarmPreference(this)
        binding.switchRemainder.isChecked = remainderPreference.getRemainderAlarm().remainded

        alarmReceiver = AlarmReceiver()
        checkAlarmRemainder()
        binding.selectLanguage.setOnClickListener(this)

    }

    private fun checkAlarmRemainder() {
        binding.switchRemainder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveRemainder(true)
                alarmReceiver.setRepeatingAlarm(this, "RepeatingAlarm", "9:00", "Github Remainder")
            } else {
                saveRemainder(false)
                alarmReceiver.cancelAlarm(this)
            }

        }
    }

    private fun customActionBarSetting() {
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.settings)
    }

    private fun saveRemainder(state: Boolean) {
        val remainderPreference = AlarmPreference(this)
        reminder = RemainderModel()
        reminder.remainded = state
        remainderPreference.setReminderAlarm(reminder)
    }


    private fun settingLanguage() {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.selectLanguage -> {
                settingLanguage()
            }
        }
    }
}