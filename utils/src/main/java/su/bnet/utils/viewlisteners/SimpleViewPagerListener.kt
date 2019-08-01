package su.bnet.utils.viewlisteners

import android.support.v4.view.ViewPager

/**
 * pageListener на лямбдах
 */
class SimpleViewPagerListener @JvmOverloads constructor(
        var selected: (Int) -> Unit,
        var scrolled: (Int, Float, Int) -> Unit = { p, po, pop -> },
        var stateChanged: (Int) -> Unit = {}) : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
        stateChanged.invoke(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        scrolled.invoke(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        selected.invoke(position)
    }
}