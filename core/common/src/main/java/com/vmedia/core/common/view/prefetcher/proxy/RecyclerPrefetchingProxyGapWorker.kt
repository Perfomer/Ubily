@file:Suppress("PackageDirectoryMismatch")
package androidx.recyclerview.widget

internal val gapWorker: GapWorker?
    get() = GapWorker.sGapWorker.get()

internal fun isGapWorker(runnable: Runnable) = runnable is GapWorker

