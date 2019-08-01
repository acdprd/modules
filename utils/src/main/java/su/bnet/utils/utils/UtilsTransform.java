package su.bnet.utils.utils;

import android.support.annotation.NonNull;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;

import java.util.*;

/**
 * вспомогательные методы для преобразования коллекций в другие коллекции
 */
public class UtilsTransform {
    private static final String TAG = UtilsTransform.class.getSimpleName();

    /**
     * @param list - list<Object>
     * @param <T>  - object extends ListItem
     * @return - list<ListItem>
     */
//    public static <T extends ListItem> List<ListItem> asListItems(List<T> list) {
//        List<ListItem> res = new ArrayList<>();
//        res.addAll(list);
//        return res;
//    }

    /**
     * @param list - list<T>
     * @param <T>  - object
     * @return - list<CheckableItem<T>>
     */
//    public static <T> List<CheckableItem<T>> asCheckableItems(List<T> list) {
//        List<CheckableItem<T>> res = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            res.add(new CheckableItem<>(list.get(i)));
//        }
//        return res;
//    }

    /**
     * @param array    - массив элементов для представления в виде строк
     * @param toString - функция для преобразования элемента в строку
     * @return - список строк
     */
    public static <T> List<String> asStringList(T[] array, Function1<T, String> toString) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            res.add(toString.invoke(array[i]));
        }
        return res;
    }

    /**
     * @param list     - список элементов для представления в виде строк
     * @param toString - функция для преобразования элемента в строку
     * @return - список строк
     */
    public static <T> List<String> asStringList(List<T> list, Function1<T, String> toString) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            res.add(toString.invoke(list.get(i)));
        }
        return res;
    }

    /**
     * @param from      - map<K,V>
     * @param transform - function to transform
     * @param <K>       - key type
     * @param <V>       - value type
     * @param <T>       - returned list type
     * @return - list<T>
     */
    @NonNull
    public static <K, V, T> List<T> mapAsList(Map<K, V> from, Function1<Pair<K, V>, T> transform) {
        List<T> res = new ArrayList<>();
        for (Map.Entry<K, V> m : from.entrySet()) {
            res.add(transform.invoke(new Pair<>(m.getKey(), m.getValue())));
        }

        return res;
    }

    /**
     * @param from      - map<K,V>
     * @param transform - function to transform
     * @param <K>       - key type
     * @param <V>       - value type
     * @param <KT>      - returned map key type
     * @param <VT>      - returned map value type
     * @return - list<T>
     */
    @NonNull
    public static <K, V, KT, VT> Map<KT, VT> mapAsMap(Map<K, V> from, Function1<Pair<K, V>, Pair<KT, VT>> transform) {
        Map<KT, VT> res = new LinkedHashMap<>();
        for (Map.Entry<K, V> m : from.entrySet()) {
            Pair<KT, VT> p = transform.invoke(new Pair<>(m.getKey(), m.getValue()));
            res.put(p.getFirst(), p.getSecond());
        }

        return res;
    }

    /**
     * Преобразовать коллекцию элементов в другой список (например обернуть в объекты классов-оберток)
     *
     * @param collection     - коллекция входящих элементов для представления
     * @param toOtherList - функция для преобразования элемента в другой элемент
     * @return - список новых элементов указанного типа
     */
    public static <T, M> List<M> transformTo(Collection<T> collection, Function1<T, M> toOtherList) {
        List<M> arrayList = new ArrayList<>();
        if(collection == null) {
            return arrayList;
        }
        for (T item : collection) {
            arrayList.add(toOtherList.invoke(item));
        }
        return arrayList;
    }

    /**
     * Преобразовать список элементов в хеш-таблицу
     *
     * @param keyFromObject - функция для извлечения ключа из объекта (как правило, это будет поле d)
     * @return - сгенерированная хеш-таблица
     */
    public static <K, V> Map<K, V> transformListToMap(List<V> list, Function1<V, K> keyFromObject) {
        Map<K, V> map = new HashMap<>();
        for (V item : list) {
            map.put(keyFromObject.invoke(item), item);
        }
        return map;
    }
}
