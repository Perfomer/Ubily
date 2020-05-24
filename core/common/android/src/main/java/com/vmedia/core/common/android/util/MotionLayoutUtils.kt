package com.vmedia.core.common.android.util

import androidx.constraintlayout.motion.widget.MotionLayout

fun MotionLayout.onAnimationFinished(onlyOnce: Boolean = true, block: () -> Unit) {
    var finished = false
    setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = Unit
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) = Unit
        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if (!onlyOnce || !finished) block.invoke()
            finished = true
        }
    })
}