package su.bnet.utils.viewlisteners

import android.support.design.widget.TabLayout

/**
 * by acdprd | 30.04.2019.
 */

class LambdaTabListener @JvmOverloads constructor(
        var selected: (TabLayout.Tab) -> Unit,
        var reSelected: (TabLayout.Tab) -> Unit = {},
        var unSelected: (TabLayout.Tab?) -> Unit = {}
) : TabLayout.OnTabSelectedListener {

    override fun onTabReselected(p0: TabLayout.Tab?) {
       p0?.let {  reSelected.invoke(p0)}
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
        unSelected.invoke(p0)
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
       p0?.let{ selected.invoke(p0)}
    }
}