package su.bnet.utils.extensions

import java.util.*
import kotlin.reflect.KMutableProperty1

/**
 * рандомный элемент из коллекции
 */
fun <T : Any> List<T>.randomElement() = this[Random().nextInt(size)]

/**
 * рандомный элемент из массива
 */
fun <T : Any> Array<T>.randomElement() = this[Random().nextInt(size)]

//todo фактически filter{it.property!=null}
fun <T, R : Any, P : Any?> Iterable<T>.mapNotNullProperty(
    property: KMutableProperty1<T, P?>,
    transform: (T) -> R?
): List<R> {
    return this.mapNotNull { if (property.get(it) != null) transform.invoke(it) else null }
}

//fun <T : Any> Iterable<T>.asCheckableItems(): MutableList<CheckableItem<T>> =
//    map { CheckableItem(it) }.toMutableList()
//
//fun <T : ListItem> Iterable<T>.asListItems(): MutableList<ListItem> =
//    map { it as ListItem }.toMutableList()
