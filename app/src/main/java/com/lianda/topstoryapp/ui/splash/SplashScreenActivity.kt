package com.lianda.topstoryapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.ui.topstories.TopStoriesActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch {
            delay(2000)
            TopStoriesActivity.start(this@SplashScreenActivity)
            finish()
        }
    }
}
