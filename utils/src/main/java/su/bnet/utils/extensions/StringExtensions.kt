package su.bnet.utils.extensions

import su.bnet.smartpot.Consts


/**
 * первую букву в upperCase
 */
fun String.firstCharToUpper(): String = Utils.firstCharToUpper(this)

fun String?.onlyDigits(): String? = if (this == null) null else Utils.onlyDigits(this)

/**
 * возможно добавляет начало(полный урл) ссылки на изображение
 */
fun String?.tryFixImageUrl(): String? = if (this == null) this else if (!this.startsWith("http")) buildString {
    append(
        Consts.Network.BASE_URL
    ).append(this@tryFixImageUrl)
} else this

fun String.tryRemovePlusFromStart(): String = if (this.startsWith("+")) this.substring(1) else this

fun String.tryRemoveSevenFromStart(): String = if (this.startsWith("7")) this.substring(1) else this

fun String.tryRemovePlusAndSevenFromStart(): String = if (this.startsWith("+7")) this.substring(2) else this

fun String.tryAddPlusToStart(): String =
    if (this.startsWith("+")) this else buildString { append("+").append(this@tryAddPlusToStart) }

fun String.formatPhoneNumberWithBrackets(): String =
    if (Utils.isPhoneValid(this.tryRemovePlusFromStart())) Utils.formatPhoneNumberWithRoundBrackets(this.tryRemovePlusFromStart()) else this /*throw IllegalArgumentException("$this is not a phone number")*/

fun String.formatPhoneNumber(): String =
    if (Utils.isPhoneValid(this.tryRemovePlusFromStart())) Utils.formatPhoneNumber(this.tryRemovePlusFromStart()) else this /*throw IllegalArgumentException("$this is not a phone number")*/

fun String.endsWith(suffix: String?):Boolean{
    suffix?.let {
       return endsWith(suffix,false)
    }?:let {
        return false
    }
}