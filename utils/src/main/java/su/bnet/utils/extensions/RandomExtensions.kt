package su.bnet.utils.extensions

import java.util.*
import kotlin.collections.ArrayList

fun Random.nextChar(randomUppercase: Boolean = true) =
    if (randomUppercase && nextBoolean()) (nextInt(26) + 97).toChar().toUpperCase() else (nextInt(26) + 97).toChar()

fun kotlin.random.Random.nextChar(randomUppercase: Boolean = true) =
    if (randomUppercase && nextBoolean()) (nextInt(26) + 97).toChar().toUpperCase() else (nextInt(26) + 97).toChar()

/**
 * рандомный символ из кирриллицы (возможно не все)
 */
fun Random.nextCharRu(randomUppercase: Boolean = true): Char {
    val array = ArrayList<Char>()
    val ar2 = ArrayList<Int>(IntRange(1072, 1103).toMutableList())
    for (i in ar2) {
        array.add(i.toChar())
    }
    return array.randomElement()
}

fun kotlin.random.Random.nextCharRu(randomUppercase: Boolean = true): Char {
    val array = ArrayList<Char>()
    val ar2 = ArrayList<Int>(IntRange(1072, 1103).toMutableList())
    for (i in ar2) {
        array.add(i.toChar())
    }
    return array.randomElement()
}