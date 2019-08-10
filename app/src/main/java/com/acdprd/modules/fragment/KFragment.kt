package com.acdprd.modules.fragment

import android.Manifest
import android.support.v4.app.Fragment
import su.bnet.utils.permission.IPermissionHelper
import su.bnet.utils.permission.PermissionListener
import su.bnet.utils.permission.PermissionsHelper

class KFragment : Fragment(), IPermissionHelper {
    private var pListener: PermissionListener? = null


    override fun setPermissionListener(l: PermissionListener) {
        pListener = l
    }


    private fun test() {
        PermissionsHelper.check(this,1,Manifest.permission.READ_EXTERNAL_STORAGE){ granted ->
            handle(granted)
        }
    }

    private fun handle(granted: Boolean) {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pListener?.handlePermissionResponse(requestCode, grantResults)
    }
}