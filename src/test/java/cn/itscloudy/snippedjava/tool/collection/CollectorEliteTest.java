package cn.itscloudy.snippedjava.tool.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectorEliteTest {

    public static final User JACK = new User(1, 10, "JACK");
    public static final User ROSE = new User(2, 12, "ROSE");
    public static final User JULIET = new User(3, 10, "JULIET");
    public static final Collection<User> USERS = new ArrayList<>() {{
        add(JACK);
        add(ROSE);
        add(JULIET);
    }};

    @Test
    public void shouldMapKeyFieldMap() {
        Map<Integer, String> idNameMap = CollectorElite.map(USERS, User::getId, User::getName);
        assertEquals("JACK", idNameMap.get(1));
        assertEquals("ROSE", idNameMap.get(2));
        assertEquals("JULIET", idNameMap.get(3));
    }

    @Test
    public void shouldMapKeyObjectMap() {
        Map<Integer, User> idNameMap = CollectorElite.map(USERS, User::getId);
        assertEquals("JACK", idNameMap.get(1).getName());
        assertEquals("ROSE", idNameMap.get(2).getName());
        assertEquals("JULIET", idNameMap.get(3).getName());
    }

    @Test
    public void shouldMapList() {
        List<Integer> ids = CollectorElite.mapList(USERS, User::getId);
        List<String> names = CollectorElite.mapList(USERS, User::getName);
        assertListValues(ids, 1, 2, 3);
        assertListValues(names, "JACK", "ROSE", "JULIET");
    }

    @Test
    public void shouldMapSet() {
        Set<Integer> ids = CollectorElite.mapSet(USERS, User::getId);
        HashSet<String> names = CollectorElite.mapSet(USERS, User::getName);
        assertSetContains(ids, 1, 2, 3);
        assertSetContains(names, "JACK", "ROSE", "JULIET");
    }

    @Test
    public void shouldClassify() {
        HashMap<Integer, List<User>> classification = CollectorElite.classify(USERS, User::getAge);
        assertEquals(2, classification.get(10).size());
        assertEquals("JACK", classification.get(10).get(0).getName());
        assertEquals("JULIET", classification.get(10).get(1).getName());
        assertEquals(1, classification.get(12).size());
        assertEquals("ROSE", classification.get(12).get(0).getName());
    }

    @Test
    public void shouldClassifyFiled() {
        HashMap<Integer, List<String>> classification = CollectorElite.classify(USERS, User::getAge, User::getName);
        assertEquals(2, classification.get(10).size());
        assertEquals("JACK", classification.get(10).get(0));
        assertEquals("JULIET", classification.get(10).get(1));
        assertEquals(1, classification.get(12).size());
        assertEquals("ROSE", classification.get(12).get(0));
    }

    @SafeVarargs
    private <T> void assertListValues(List<T> list, T... ts) {
        assertEquals(ts.length, list.size());
        for (int i = 0; i < ts.length; i++) {
            assertEquals(ts[i], list.get(i));
        }
    }

    @SafeVarargs
    private <T> void assertSetContains(Set<T> set, T... ts) {
        assertEquals(ts.length, set.size());
        for (T t : ts) {
            assertTrue(set.contains(t));
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    private static class User {

        private int id;

        private int age;

        private String name;

    }

}
