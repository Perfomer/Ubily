package com.vmedia.ubily

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vmedia.feature.auth.DI_FRAGMENT_AUTH
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.nav_host_fragment, get(DI_FRAGMENT_AUTH))
            .commit()
    }

}