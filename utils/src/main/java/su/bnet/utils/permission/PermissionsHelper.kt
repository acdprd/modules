package su.bnet.utils.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment


/**
 * Created by Timur Khakimov on 01.03.2019.
 * Класс для работы с пермишнами из фрагментов.
 * Объект создается в фрагменте и результат "разрешено/не разрешено" обрабатывается с помощью переопределения метода onResult(boolean isGranted)
 */
object PermissionsHelper {

    fun <F> check(
        fragment: F,
        requestCode1: Int,
        permission: String,
        onResult:(Boolean)->Unit
    ) where F : IPermissionHelper, F : Fragment {
        fragment.activity?.let { activity ->
            val selfPermission = ActivityCompat.checkSelfPermission(activity, permission)
            if (selfPermission == PackageManager.PERMISSION_GRANTED) {
                onResult(true)
            } else {
                fragment.setPermissionListener(object : PermissionListener {
                    override fun handlePermissionResponse(
                        requestCode: Int,
                        grantResults: IntArray
                    ) {
                        Permissions.handleResponse(
                            requestCode1,
                            requestCode,
                            grantResults,
                            onResult
                        )
                    }
                })
                Permissions.requestFragment(fragment, permission, requestCode1,onResult)
            }
        }

        fun <A> check(
            activity: A?,
            requestCode1: Int,
            permission: String,
            onResult:(Boolean)->Unit
        ) where A : IPermissionHelper, A : Activity {
            activity?.let {
                val selfPermission = ActivityCompat.checkSelfPermission(activity, permission)
                if (selfPermission == PackageManager.PERMISSION_GRANTED) {
                    onResult(true)
                } else {
                    activity.setPermissionListener(object : PermissionListener {
                        override fun handlePermissionResponse(
                            requestCode: Int,
                            grantResults: IntArray
                        ) {
                            Permissions.handleResponse(
                                requestCode1,
                                requestCode,
                                grantResults,
                                onResult
                            )
                        }
                    })
                    Permissions.request(activity, permission, requestCode1,onResult)
                }
            }
        }
    }
}
