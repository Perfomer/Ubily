package com.vmedia.core.common.pure.util.rx

import io.reactivex.Completable

fun Completable.andThenMerge(vararg completables: Completable): Completable {
    return andThen(Completable.mergeArray(*completables))
}
