package su.bnet.utils.extensions

import su.bnet.utils.utils.Utils


/**
 * первую букву в upperCase
 */
fun String.firstCharToUpper(): String = Utils.firstCharToUpper(this)

fun String?.onlyDigits(): String? = if (this == null) null else Utils.onlyDigits(this)

fun String?.isNullOrEmptyOrBlank(): Boolean = this == null || this.isEmpty() || this.isBlank()

fun String?.isNotNullNotEmptyNotBlank(): Boolean = !this.isNullOrEmptyOrBlank()

fun String?.tryRemovePlusFromStart(): String? {
    return when {
        this == null -> null
        this.startsWith("+") -> try {
            this@tryRemovePlusFromStart.substring(1)
        } catch (e: Exception) {
            this
        }
        else -> this
    }
}

fun String?.tryRemoveSevenFromStart(): String? {
    return when {
        this == null -> null
        this.startsWith("7") -> try {
            this@tryRemoveSevenFromStart.substring(1)
        } catch (e: Exception) {
            this
        }
        else -> this
    }
}


fun String?.tryRemovePlusAndSevenFromStart(): String? {
    return when {
        this == null -> null
        this.startsWith("+7") -> try {
            this@tryRemovePlusAndSevenFromStart.substring(2)
        } catch (e: Exception) {
            this
        }
        else -> this
    }
}


fun String?.tryAddPlusToStart(): String? {
    return when {
        this == null -> null
        this.startsWith("+") -> this
        else -> buildString { append("+").append(this@tryAddPlusToStart) }
    }
}


fun String?.tryReplaceEightFromStart(): String? {
    return when {
        this == null -> null
        this.startsWith("8") -> try {
            buildString { append("7").append(this@tryReplaceEightFromStart.substring(1)) }
        } catch (e: Exception) {
            this
        }
        else -> this
    }
}

fun String.endsWith(suffix: String?): Boolean {
    suffix?.let {
        return endsWith(suffix, false)
    } ?: let {
        return false
    }
}
