package com.vmedia.ubily

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.vmedia.core.data.internal.network.UnityRssApi
import com.vmedia.feature.auth.DI_FRAGMENT_AUTH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.nav_host_fragment, get(DI_FRAGMENT_AUTH))
            .commit()

        nav_host_fragment.postDelayed(0) {
            get<UnityRssApi>().getCommentsRss("wello-graphics", "ayrtrVzN1cIfk44lSmoJuOFlOSA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
//                        val i = 0
                    },
                    onSuccess = {
//                        val i = 0
                    }
                )
        }
    }

}