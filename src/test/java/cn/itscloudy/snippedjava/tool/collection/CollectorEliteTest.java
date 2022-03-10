package cn.itscloudy.snippedjava.tool.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectorEliteTest {

    static final User JACK = new User(1, 10, "JACK");
    static final User ROSE = new User(2, 12, "ROSE");
    static final User JULIET = new User(3, 10, "JULIET");
    static final List<User> USERS = new ArrayList<>();

    static final User ANIKA = new User(11, 38, "ANIKA");
    static final User ERICK = new User(12, 67, "ERICK");
    static final User MAGGIE = new User(13, 24, "MAGGIE");
    static final User EBBA = new User(14, 41, "EBBA");
    static final User AKI = new User(15, 25, "AKI");
    static final List<User> POLARIS6_CREW = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        USERS.clear();
        USERS.add(JACK);
        USERS.add(ROSE);
        USERS.add(JULIET);

        POLARIS6_CREW.clear();
        POLARIS6_CREW.add(ANIKA);
        POLARIS6_CREW.add(ERICK);
        POLARIS6_CREW.add(MAGGIE);
        POLARIS6_CREW.add(EBBA);
        POLARIS6_CREW.add(AKI);
    }

    @Test
    void shouldMapKeyFieldMap() {
        Map<Integer, String> idNameMap = CollectorElite.map(USERS, User::getId, User::getName);
        assertEquals("JACK", idNameMap.get(1));
        assertEquals("ROSE", idNameMap.get(2));
        assertEquals("JULIET", idNameMap.get(3));
    }

    @Test
    void shouldMapKeyObjectMap() {
        Map<Integer, User> idNameMap = CollectorElite.map(USERS, User::getId);
        assertEquals("JACK", idNameMap.get(1).getName());
        assertEquals("ROSE", idNameMap.get(2).getName());
        assertEquals("JULIET", idNameMap.get(3).getName());
    }

    @Test
    void shouldMapList() {
        List<Integer> ids = CollectorElite.mapList(USERS, User::getId);
        List<String> names = CollectorElite.mapList(USERS, User::getName);
        assertListValues(ids, 1, 2, 3);
        assertListValues(names, "JACK", "ROSE", "JULIET");
    }

    @Test
    void shouldMapListWithSkip() {
        List<Integer> ids = CollectorElite.mapList(USERS, User::getId, u -> u.getAge() == 10);
        List<String> names = CollectorElite.mapList(USERS, User::getName, u -> u.getName().length() == 4);
        assertListValues(ids, 1, 3);
        assertListValues(names, "JACK", "ROSE");
    }


    @Test
    void shouldMapSet() {
        Set<Integer> ids = CollectorElite.mapSet(USERS, User::getId);
        Set<String> names = CollectorElite.mapSet(USERS, User::getName);
        assertSetContains(ids, 1, 2, 3);
        assertSetContains(names, "JACK", "ROSE", "JULIET");
    }

    @Test
    void shouldClassify() {
        Map<Integer, List<User>> classification = CollectorElite.classify(USERS, User::getAge);
        assertEquals(2, classification.get(10).size());
        assertEquals("JACK", classification.get(10).get(0).getName());
        assertEquals("JULIET", classification.get(10).get(1).getName());
        assertEquals(1, classification.get(12).size());
        assertEquals("ROSE", classification.get(12).get(0).getName());
    }

    @Test
    void shouldClassifyFiled() {
        Map<Integer, List<String>> classification = CollectorElite.classify(USERS, User::getAge, User::getName);
        assertEquals(2, classification.get(10).size());
        assertEquals("JACK", classification.get(10).get(0));
        assertEquals("JULIET", classification.get(10).get(1));
        assertEquals(1, classification.get(12).size());
        assertEquals("ROSE", classification.get(12).get(0));
    }

    @Test
    void shouldMapFlatList() {
        List<Character> chars = CollectorElite.mapFlatList(USERS, User::nameChars);
        assertListValues(chars, 'J', 'A', 'C', 'K', 'R', 'O', 'S', 'E', 'J', 'U', 'L', 'I', 'E', 'T');
    }

    @Test
    void shouldMapFlatSet() {
        Set<Character> chars = CollectorElite.mapFlatSet(USERS, User::nameChars);
        assertSetContains(chars, 'J', 'A', 'C', 'K', 'R', 'O', 'S', 'E', 'L', 'U', 'I', 'T');
    }

    @Test
    void shouldGetCyclicSublist() {
        List<User> users = CollectorElite.cyclicSubList(USERS, 2, 5);
        assertEquals(5, users.size());
        assertEquals("JULIET", users.get(0).getName());
        assertEquals("JACK", users.get(1).getName());
        assertEquals("ROSE", users.get(2).getName());
        assertEquals("JULIET", users.get(3).getName());
        assertEquals("JACK", users.get(4).getName());
    }

    @Test
    void shouldGetSubList() {
        List<User> users = CollectorElite.subAmount(USERS, 2, 5);
        List<User> users1 = CollectorElite.subAmount(USERS, 3, 2);
        assertEquals(1, users.size());
        assertEquals("JULIET", users.get(0).getName());
        assertEquals(0, users1.size());
    }

    @Test
    void shouldGetIntersection() {
        List<User> newPolaris6Crew = new ArrayList<>();
        Collections.copy(POLARIS6_CREW, newPolaris6Crew);
        newPolaris6Crew.add(ROSE);
        newPolaris6Crew.add(JULIET);

        List<User> intersection = CollectorElite.intersect(USERS, newPolaris6Crew);
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains(ROSE));
        assertTrue(intersection.contains(JULIET));
    }

    @Test
    void shouldListIntersectedByComparator() {
        List<User> intersection = CollectorElite.intersect(POLARIS6_CREW, USERS,
                (u1, u2) -> u1.getName().length() == u2.getName().length());

        assertEquals(2, intersection.size());
        assertEquals("MAGGIE", intersection.get(0).getName());
        assertEquals("EBBA", intersection.get(1).getName());
    }

    @Test
    void shouldGetSubtraction() {
        List<User> newPolaris6Crew = new ArrayList<>();
        Collections.copy(POLARIS6_CREW, newPolaris6Crew);
        newPolaris6Crew.add(JACK);
        newPolaris6Crew.add(ROSE);

        List<User> subtraction = CollectorElite.difference(newPolaris6Crew, POLARIS6_CREW);
        assertEquals(2, subtraction.size());
        assertTrue(subtraction.contains(JACK));
        assertTrue(subtraction.contains(ROSE));
    }

    @Test
    void shouldGetListSubtractedByComparator() {
        List<User> subtraction = CollectorElite.difference(POLARIS6_CREW, USERS,
                (u1, u2) -> u1.getName().length() == u2.getName().length());

        assertEquals(3, subtraction.size());
        assertTrue(subtraction.contains(ANIKA));
        assertTrue(subtraction.contains(ERICK));
        assertTrue(subtraction.contains(AKI));

    }

    @Test
    void shouldFilterCollection() {
        List<User> crewAgeLt30 = CollectorElite.filter(POLARIS6_CREW, u -> u.getAge() < 30);
        assertEquals(2, crewAgeLt30.size());
        assertTrue(crewAgeLt30.contains(MAGGIE));
        assertTrue(crewAgeLt30.contains(AKI));
    }

    @Test
    void shouldFilterCollectionAndGetTheRest() {
        List<User> crewAgeGe30 = new ArrayList<>();
        List<User> crewAgeLt30 = CollectorElite.filter(POLARIS6_CREW, u -> u.getAge() < 30, crewAgeGe30);
        assertEquals(2, crewAgeLt30.size());
        assertTrue(crewAgeLt30.contains(MAGGIE));
        assertTrue(crewAgeLt30.contains(AKI));

        assertEquals(3, crewAgeGe30.size());
        assertTrue(crewAgeGe30.contains(ERICK));
        assertTrue(crewAgeGe30.contains(ANIKA));
        assertTrue(crewAgeGe30.contains(EBBA));
    }

    @Test
    void shouldCountDistinct() {
        int count = CollectorElite.count(POLARIS6_CREW, u -> u.getName().length());
        assertEquals(4, count);
    }

    @Test
    void shouldGetMapKeysByValue() {
        HashMap<Integer, Character> map = new HashMap<>();
        map.put(1, 'A');
        map.put(2, 'B');
        map.put(3, 'C');

        Set<Integer> keys = CollectorElite.mapKeysByValue(map, c -> c >= 'B');
        assertEquals(2, keys.size());
        assertTrue(keys.contains(2));
        assertTrue(keys.contains(3));
    }

    @Test
    void shouldReduce() {
        Integer totalAge = CollectorElite.reduce(POLARIS6_CREW, 0, (u, a) -> u.getAge() + a);
        assertEquals(38 + 67 + 24 + 41 + 25, totalAge);
    }

    @Test
    void shouldReduceWithPredicate() {
        Integer totalAge = CollectorElite.reduce(POLARIS6_CREW, u -> u.getAge() > 30, 0,
                (u, a) -> u.getAge() + a);
        assertEquals(38 + 67 + 41, totalAge);
    }

    @Test
    void shouldJoin() {
        String joinedUsers = CollectorElite.join(USERS, " X ");
        assertEquals("JACK[10] X ROSE[12] X JULIET[10]", joinedUsers);
    }

    @Test
    void shouldDistinctByComparator() {
        List<User> distinctUsers = CollectorElite.distinctByComparator(POLARIS6_CREW,
                (u1, u2) -> u1.getName().length() == u2.getName().length());
        assertEquals(4, distinctUsers.size());
        assertTrue(distinctUsers.contains(ANIKA));
        assertTrue(distinctUsers.contains(MAGGIE));
        assertTrue(distinctUsers.contains(EBBA));
        assertTrue(distinctUsers.contains(AKI));
    }

    @Test
    void shouldFindAnyMatched() {
        User user = CollectorElite.findAny(POLARIS6_CREW, u -> u.getAge() > 60).orElse(null);
        assertNotNull(user);
        assertEquals(ERICK, user);
    }

    @Test
    void shouldFindNothing() {
        User user = CollectorElite.findAny(POLARIS6_CREW, u -> u.getAge() > 100).orElse(null);
        assertNull(user);
    }

    @Test
    void shouldFindMaxField() {
        Integer maxAge = CollectorElite.findMaxField(POLARIS6_CREW, User::getAge);
        assertEquals(ERICK.getAge(), maxAge);
    }

    @Test
    void shouldFindMinField() {
        Integer minAge = CollectorElite.findMinField(POLARIS6_CREW, User::getAge);
        assertEquals(MAGGIE.getAge(), minAge);
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

        public List<Character> nameChars() {
            List<Character> chars = new ArrayList<>();
            for (char c : name.toCharArray()) {
                chars.add(c);
            }
            return chars;
        }

        @Override
        public String toString() {
            return getName() + "[" + age + "]";
        }
    }

}
