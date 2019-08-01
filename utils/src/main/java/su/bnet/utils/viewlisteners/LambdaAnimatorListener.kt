package su.bnet.utils.viewlisteners

import android.animation.Animator

class LambdaAnimatorListener(
    var repeat: () -> Unit = {},
    var cancel: () -> Unit = {},
    var start: () -> Unit = {},
    var end: () -> Unit = {}
) : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
        repeat.invoke()
    }

    override fun onAnimationEnd(animation: Animator?) {
        end.invoke()
    }

    override fun onAnimationCancel(animation: Animator?) {
        cancel.invoke()
    }

    override fun onAnimationStart(animation: Animator?) {
        start.invoke()
    }
}