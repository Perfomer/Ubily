package com.vmedia.core.common.android.view

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vmedia.core.common.android.view.system.SystemUiColorMode
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment(
    @LayoutRes private val layoutResource: Int? = null,
    @MenuRes private val menuResource: Int? = null
) : Fragment() {

    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

    protected val disposable by lazy { CompositeDisposable() }

    protected var systemUiColorMode: SystemUiColorMode
        get() = baseActivity.systemUiColorMode
        set(value) {
            baseActivity.systemUiColorMode = value
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuResource?.let { setHasOptionsMenu(true) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutResource == null) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            inflater.inflate(layoutResource, container, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuResource?.let { inflater.inflate(it, menu) }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> goBack()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }


    protected fun goBack() {
        activity?.onBackPressed()
    }

    protected fun Toolbar.attachToActivity(enableArrowUp: Boolean = true) {
        baseActivity.setSupportActionBar(this)
        baseActivity.supportActionBar?.setDisplayHomeAsUpEnabled(enableArrowUp)
    }

}