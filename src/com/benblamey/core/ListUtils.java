package com.benblamey.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Utility methods for Lists
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class ListUtils {

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    public static <T> List<T> intersection(Collection<T> list1, Collection<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static <T> T first(Iterable<T> list1) {
        Iterator<T> iterator = list1.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    public static <T1, T2> List<T2> cast(List<T1> inputs) {
        List<T2> result = new ArrayList<>();
        for (T1 input : inputs) {
            result.add((T2)input);
        }
        return result;
    }
    
    

}
