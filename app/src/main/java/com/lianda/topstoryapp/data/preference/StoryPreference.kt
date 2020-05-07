package com.lianda.topstoryapp.data.preference

import android.content.Context
import android.content.SharedPreferences

class StoryPreference(context: Context) {

    private val PREFERENCES_NAME = "Story Preference"

    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String, default: String): String {
        return preference.getString(key, default)!!
    }

    fun saveString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

}