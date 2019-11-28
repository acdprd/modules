package su.bnet.utils.viewlisteners

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

/**
 * Created by acdprd on 06.12.2017.
 */

open class OnSwipeTouchListener(context: Context) : View.OnTouchListener {
    private val gestureDetector: GestureDetector
    private var threshold = SWIPE_THRESHOLD
    private var velocityThreshold = SWIPE_VELOCITY_THRESHOLD
    private var longPress: Boolean = false

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return if (longPress) {
            checkAfterLongTap(event)
        } else
            gestureDetector.onTouchEvent(event)
    }

    private fun checkAfterLongTap(event: MotionEvent): Boolean {
        if (longPress && event.action == MotionEvent.ACTION_UP) {
            longPress = false
            onLongUnTap()
            return true
        }
        return false
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


//        override fun onDoubleTap(e: MotionEvent): Boolean {
////            this@OnSwipeTouchListener.onDoubleTap(e.x, e.y)
////            return super.onDoubleTap(e)
////        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            this@OnSwipeTouchListener.onSingleTap(e.x, e.y)
            return super.onSingleTapConfirmed(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            this@OnSwipeTouchListener.onDoubleTap(e.x, e.y)
            return super.onDoubleTapEvent(e)
        }

        override fun onLongPress(e: MotionEvent) {
            longPress = true
            this@OnSwipeTouchListener.onLongTap()
            super.onLongPress(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > threshold && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (abs(diffY) > velocityThreshold && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }
    }

    fun setThreshold(value: Int) {
        threshold = value
    }

    fun setVeloscityThreshold(value: Int) {
        velocityThreshold = value
    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
    open fun onSwipeTop() {}
    open fun onSwipeBottom() {}
    open fun onSingleTap(x: Float, y: Float) {}
    open fun onDoubleTap(x: Float, y: Float) {}
    open fun onLongTap() {}
    open fun onLongUnTap() {}


    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }
}