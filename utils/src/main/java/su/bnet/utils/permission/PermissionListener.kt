package su.bnet.utils.permission

/**
 * Created by Timur Khakimov on 01.03.2019.
 * Интерфейс для отслеживания событий Permission
 */
interface PermissionListener {
    fun handlePermissionResponse(requestCode: Int, grantResults: IntArray)
}
