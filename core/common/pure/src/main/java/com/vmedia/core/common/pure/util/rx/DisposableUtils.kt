package com.vmedia.core.common.pure.util.rx

import io.reactivex.disposables.Disposable

fun Disposable?.safeDispose() {
    if (this?.isDisposed == false) dispose()
}