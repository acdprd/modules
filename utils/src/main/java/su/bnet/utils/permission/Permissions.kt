package su.bnet.utils.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import su.bnet.utils.helper.Logg


/**
 * Created by Timur Khakimov on 01.03.2019.
 * Основной класс для работы с Permissions
 */
object Permissions {

    private val TAG = Permissions::class.java.simpleName

    fun requestFragment(fragment: Fragment, permissions: String, code: Int, grantedResult: (Boolean) -> Unit) {
        val currentApiVersion = android.os.Build.VERSION.SDK_INT
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            fragment.activity?.let { activity ->
                val selfPermission = ContextCompat.checkSelfPermission(activity, permissions)
                if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                    fragment.requestPermissions(arrayOf(permissions), code)
                } else {
                    grantedResult.invoke(true)
                }
            }
        } else {
            grantedResult.invoke(true)
        }
    }

    fun request(activity: Activity, permissions: String, code: Int, grantedResult: (Boolean) -> Unit) {
        val currentApiVersion = android.os.Build.VERSION.SDK_INT
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            val selfPermission = ContextCompat.checkSelfPermission(activity, permissions)
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions)) {

                } /*else*/
                run { ActivityCompat.requestPermissions(activity, arrayOf(permissions), code) }
            } else {
                grantedResult.invoke(true)
            }
        } else {
            grantedResult.invoke(true)
        }
    }

    fun handleResponse(
        requestCode: Int,
        code: Int,
        grantResults: IntArray,
        grantedResult: (Boolean)->Unit
    ) {
        if (requestCode == code) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logg.d(TAG, "granted")
                grantedResult.invoke(true)
            } else {
                Logg.d(TAG, "not granted")
                grantedResult.invoke(false)
            }
        }
    }

    object Request {
        val CAMERA = 1101
        val PHOTO_FROM_CAMERA = 1102
        val PHOTO_FROM_GALLERY = 1103
        val BLUETOOTH = 1104
        val BLUETOOTH_ADMIN = 1105
        val COARSE_LOCATION = 1106
        //        public static final int FINE_LOCATION = 1107;
    }
}
