package com.example.focustime

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AuthorizationFragment())
                .commit()
        }
    }

    fun goToRegister(view: View) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegistrationFragment())
            .addToBackStack(null)
            .commit()
    }

    fun goToLogin(view: View) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }
}