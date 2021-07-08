package cn.itscloudy.snippedjava.tool.collection;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectorElite {

    private CollectorElite() {
    }

    public static <O, K, V> HashMap<K, V> map(Collection<O> collection, Function<O, K> keyMapper,
                                              Function<O, V> valueMapper) {
        HashMap<K, V> map = new HashMap<>(collection.size());
        for (O o : collection) {
            map.put(keyMapper.apply(o), valueMapper.apply(o));
        }
        return map;
    }

    public static <O, K> HashMap<K, O> map(Collection<O> collection, Function<O, K> keyMapper) {
        return map(collection, keyMapper, v -> v);
    }

    public static <O, K> HashMap<K, List<O>> classify(Collection<O> collection, Function<O, K> keyMapper) {
        return classify(collection, keyMapper, o -> o);
    }

    public static <O, K, V> HashMap<K, List<V>> classify(Collection<O> collection, Function<O, K> keyMapper,
                                                         Function<O, V> valueMapper) {
        Set<K> keys = mapSet(collection, keyMapper);
        HashMap<K, List<V>> map = new HashMap<>(keys.size());
        for (K key : keys) {
            map.put(key, new ArrayList<>());
        }

        for (O o : collection) {
            K key = keyMapper.apply(o);
            map.get(key).add(valueMapper.apply(o));
        }

        return map;
    }

    public static <A, B> ArrayList<B> mapList(Collection<A> collection, Function<A, B> bMapper) {
        ArrayList<B> list = new ArrayList<>(collection.size());
        for (A a : collection) {
            list.add(bMapper.apply(a));
        }

        return list;
    }

    public static <A, B> ArrayList<B> mapList(Collection<A> collection, Function<A, B> bMapper, Predicate<A> skip) {
        ArrayList<B> list = new ArrayList<>(collection.size());
        for (A a : collection) {
            if (skip.test(a)) {
                continue;
            }
            list.add(bMapper.apply(a));
        }

        return list;
    }

    public static <A, B> ArrayList<B> mapFlatList(Collection<A> collection, Function<A, Collection<B>> bsMapper) {
        ArrayList<B> list = new ArrayList<>(collection.size());
        for (A a : collection) {
            list.addAll(bsMapper.apply(a));
        }
        return list;
    }

    public static <A, B> HashSet<B> mapSet(Collection<A> collection, Function<A, B> bMapper) {
        HashSet<B> set = new HashSet<>(collection.size());
        for (A a : collection) {
            set.add(bMapper.apply(a));
        }

        return set;
    }

    public static <A, B> HashSet<B> mapFlatSet(Collection<A> collection, Function<A, Collection<B>> bsMapper) {
        HashSet<B> set = new HashSet<>(collection.size());
        for (A a : collection) {
            set.addAll(bsMapper.apply(a));
        }
        return set;
    }

    public static <A> List<A> cyclicSubList(List<A> list, int from, int amount) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        int collectionSize = list.size();
        from = from % collectionSize;
        int to = from + amount;

        List<A> sub = list.subList(from, Math.min(collectionSize, to));
        while (sub.size() < amount) {
            sub.addAll(list.subList(0, Math.min(collectionSize, amount - sub.size())));
        }
        return sub;
    }

    public static <A> List<A> subList(List<A> list, int from, int amount) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        int collectionSize = list.size();
        int to = from + amount;
        return list.subList(Math.min(collectionSize, from), Math.min(collectionSize, to));
    }

    public static <A> List<A> intersect(Collection<A> collection, Collection<A> otherCollection) {
        return collection.stream()
                .distinct()
                .filter(otherCollection::contains)
                .collect(Collectors.toList());
    }

    public static <A> List<A> subtract(Collection<A> collection, Collection<A> otherCollection) {
        ArrayList<A> collectionList = new ArrayList<>(collection);
        for (A a : otherCollection) {
            collectionList.remove(a);
        }
        return collectionList;
    }

    public static <A> List<A> subtract(Collection<A> collection, Collection<A> otherCollection,
                                       BiPredicate<A, A> comparator) {

        ArrayList<A> collectionList = new ArrayList<>(collection);
        for (A a : otherCollection) {
            collectionList.removeIf(b -> comparator.test(a, b));
        }
        return collectionList;
    }

    public static <A> List<A> filter(Collection<A> collection, Predicate<A> filter) {
        ArrayList<A> matchedItems = new ArrayList<>();
        for (A a : collection) {
            if (filter.test(a)) {
                matchedItems.add(a);
            }
        }
        return matchedItems;
    }

    public static <A> List<A> filter(Collection<A> collection, Predicate<A> filter,
                                     Collection<A> restCollection) {
        ArrayList<A> matchedItems = new ArrayList<>();
        for (A a : collection) {
            if (filter.test(a)) {
                matchedItems.add(a);
            } else {
                restCollection.add(a);
            }
        }
        return matchedItems;
    }

    public static <A, F> int countDistinct(Collection<A> collection, Function<A, F> fMapper, Predicate<F> fPredicate) {
        Set<F> fs = new HashSet<>();
        for (A a : collection) {
            F f = fMapper.apply(a);
            if (fPredicate.test(f)) {
                fs.add(f);
            }
        }
        return fs.size();
    }

    public static <A, B> HashSet<A> mapKeysByValue(Map<A, B> map, Predicate<B> bPredicate) {
        HashSet<A> set = new HashSet<>();
        map.forEach((key, value) -> {
            if (bPredicate.test(value)) {
                set.add(key);
            }
        });
        return set;
    }

    public static <A, B> B reduce(Collection<A> as, Predicate<A> aPredicate, B identity,
                                  BiFunction<A, B, B> accumulator) {
        for (A a : as) {
            if (aPredicate.test(a)) {
                identity = accumulator.apply(a, identity);
            }
        }
        return identity;
    }

    public static <A, B> B reduce(Collection<A> as, B identity, BiFunction<A, B, B> accumulator) {
        for (A a : as) {
            identity = accumulator.apply(a, identity);
        }
        return identity;
    }

    public static String join(Collection<?> collection, String separator) {
        boolean firstSet = false;
        StringBuilder sb = new StringBuilder();

        for (Object o : collection) {
            if (firstSet) {
                sb.append(separator);
            } else {
                firstSet = true;
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static <A, F> List<A> distinct(Collection<A> as, Function<A, F> fieldGetter) {
        Set<F> fs = new HashSet<>();
        ArrayList<A> distinctAs = new ArrayList<>();
        for (A a : as) {
            if (fs.add(fieldGetter.apply(a))) {
                distinctAs.add(a);
            }
        }
        return distinctAs;
    }

    public static <A> Optional<A> findAny(Collection<A> as, Predicate<A> predicate) {
        for (A a : as) {
            if (predicate.test(a)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public static <A, F extends Comparable<F>> F maxField(Collection<A> as, Function<A, F> fieldGetter) {
        F max = null;
        for (A a : as) {
            F f = fieldGetter.apply(a);
            if (max == null) {
                max = f;
                continue;
            }
            if (f == null) {
                continue;
            }
            int i = f.compareTo(max);
            if (i > 0) {
                max = f;
            }
        }
        return max;
    }

}