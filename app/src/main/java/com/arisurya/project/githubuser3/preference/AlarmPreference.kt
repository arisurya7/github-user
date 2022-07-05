package com.arisurya.project.githubuser3.preference

import android.content.Context
import com.arisurya.project.githubuser3.data.model.RemainderModel

class AlarmPreference(context: Context) {

    companion object {
        private const val EXTRA_PREFS_NAME = "extra_prefs_name"
        private const val EXTRA_REMINDER = "extra_reminder"
    }

    private val preference = context.getSharedPreferences(EXTRA_PREFS_NAME, Context.MODE_PRIVATE)
    fun setReminderAlarm(value: RemainderModel) {
        val editor = preference.edit()
        editor.putBoolean(EXTRA_REMINDER, value.remainded)
        editor.apply()
    }

    fun getRemainderAlarm(): RemainderModel {
        val model = RemainderModel()
        model.remainded = preference.getBoolean(EXTRA_REMINDER, false)
        return model
    }

}