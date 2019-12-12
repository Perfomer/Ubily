package com.vmedia.core.common.mvi

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import com.vmedia.core.common.util.setOnClickListener
import com.vmedia.core.common.util.toObservable
import com.vmedia.core.common.view.BaseFragment
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

abstract class MviFragment<Intent : Any, State : Any, Subscription : Any>(
    @LayoutRes layoutResource: Int? = null,
    @MenuRes menuResource: Int? = null,
    private val initialIntent: Intent? = null
) : BaseFragment(layoutResource, menuResource) {

    @Volatile
    protected var currentState: State? = null
        private set

    private val viewModel by lazy(::provideViewModel)

    override fun onStart() {
        super.onStart()

        disposable.addAll(
            viewModel.state bindTo ::onStateReceived,
            viewModel.subscription bindTo ::onSubscriptionReceived
        )
    }

    override fun onResume() {
        super.onResume()
        initialIntent?.let(::postIntent)
    }

    protected abstract fun provideViewModel(): MviViewModel<Intent, *, State, Subscription>

    protected abstract fun render(state: State)

    @Synchronized
    protected open fun onSubscriptionReceived(subscription: Subscription) = Unit

    protected fun postIntent(intent: Intent) = viewModel.postIntent(intent)


    @Synchronized
    private fun onStateReceived(state: State) {
        currentState = state
        render(state)
    }


    private companion object {

        private infix fun <T : Any> ObservableSource<T>.bindTo(consumer: (T) -> Unit): Disposable {
            return this.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(onNext = consumer)
        }

    }

}