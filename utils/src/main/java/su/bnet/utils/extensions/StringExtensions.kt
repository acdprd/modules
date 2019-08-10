package su.bnet.utils.extensions

import su.bnet.utils.utils.Utils


/**
 * первую букву в upperCase
 */
fun String.firstCharToUpper(): String = Utils.firstCharToUpper(this)

fun String?.onlyDigits(): String? = if (this == null) null else Utils.onlyDigits(this)

fun String.tryRemovePlusFromStart(): String = if (this.startsWith("+")) this@tryRemovePlusFromStart.substring(1) else this

fun String.tryRemoveSevenFromStart(): String = if (this.startsWith("7")) this@tryRemoveSevenFromStart.substring(1) else this

fun String.tryRemovePlusAndSevenFromStart(): String = if (this.startsWith("+7")) this@tryRemovePlusAndSevenFromStart.substring(2) else this

fun String.tryAddPlusToStart(): String =
    if (this.startsWith("+")) this else buildString { append("+").append(this@tryAddPlusToStart) }

fun String.tryReplaceEightFromStart(): String = try {
    if (this.startsWith("8")) buildString { append("7").append(this@tryReplaceEightFromStart.substring(1)) } else this
} catch (e: Exception) {
    this
}
