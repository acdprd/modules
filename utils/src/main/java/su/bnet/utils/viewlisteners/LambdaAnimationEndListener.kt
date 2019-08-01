package su.bnet.utils.viewlisteners

import android.view.animation.Animation

/**
 * by acdprd | 23.05.2019.
 */

class LambdaAnimationEndListener(var l: () -> Unit) : AnimationEndListener() {
    override fun onAnimationEnd(p0: Animation?) {
        l.invoke()
    }
}