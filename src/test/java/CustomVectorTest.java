import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CustomVectorTest {

    @Test
    public void newEmptyVector_hasZeroSize() {
        CustomVector<Integer> customList = new CustomVector<>();
        assertEquals(0, customList.size());
    }

    @Test
    public void constructor_withNegativeCapacity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomVector<>(-1));
    }

    @Test
    public void constructor_withInitialCapacity_hasZeroSize() {
        assertEquals(0, new CustomVector<>(10).size());
    }

    @Test
    public void constructor_withNullCollection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomVector<Integer>(null));
    }

    @Test
    public void constructor_withCollectionValues_populatesVectorCorrectly() {
        Collection<Integer> values = new ArrayList<>(List.of(0, 1, 2, 3, 4));
        CustomVector<Integer> customList = new CustomVector<>(values);
        for (int i = 0; i < 5; i++) {
            assertTrue(customList.contains(i));
        }
        assertEquals(5, customList.size());
    }

    @Test
    public void addSingleElement_increasesSizeToOne() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        assertEquals(1, customVector.size());
    }

    @Test
    public void addMultipleElements_updatesSizeAndReturnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertTrue(customVector.add(1));
        assertEquals(1, customVector.size());
        assertTrue(customVector.add(2));
        assertEquals(2, customVector.size());
    }

    @Test
    public void addAtIndex_atStart_shiftsElementsCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(0, 100);
        assertEquals(100, customVector.get(0));
    }

    @Test
    public void addAtIndex_zero_insertsAtBeginning() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(0, 100);
        assertEquals(100, customVector.get(0));
    }

    @Test
    public void addAtIndex_one_insertsAtPositionOne() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(1, 100);
        assertEquals(100, customVector.get(1));
    }

    @Test
    public void addAtIndex_two_insertsAtPositionTwo() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(2, 100);
        assertEquals(100, customVector.get(2));
    }

    @Test
    public void addAtIndex_three_insertsAtEnd() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(3, 100);
        assertEquals(100, customVector.get(3));
    }

    @Test
    public void remove_outOfBoundsIndexPositive_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.remove(5));
    }

    @Test
    public void remove_outOfBoundsIndexNegative_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customList = new CustomVector<>();
        customList.add(1);
        customList.add(2);
        customList.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> customList.remove(-1));
    }

    @Test
    public void remove_validIndex_returnsRemovedElementAndResizes() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals(3, customVector.remove(2));
        assertEquals(2, customVector.size());
        assertEquals(1, customVector.get(0));
        assertEquals(2, customVector.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.get(2));
    }

    @Test
    public void remove_middleIndex_updatesSizeCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals(2, customVector.remove(1));
        assertEquals(2, customVector.size());
    }

    @Test
    public void get_negativeIndex_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.get(-1));
    }

    @Test
    public void get_indexBeyondSize_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.get(4));
    }

    @Test
    public void get_validIndices_returnsExpectedValues() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertEquals(10, customVector.get(0));
        assertEquals(20, customVector.get(1));
        assertEquals(30, customVector.get(2));
    }

    @Test
    public void addAtIndex_negativeIndex_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.add(-1, 40));
    }

    @Test
    public void addAtIndex_indexBeyondSize_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.add(100, 40));
    }

    @Test
    public void addAtIndex_one_placesElementCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(1, 40);
        assertEquals(40, customVector.get(1));
    }

    @Test
    public void addAtIndex_two_placesElementCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(2, 40);
        assertEquals(40, customVector.get(2));
    }

    @Test
    public void addAtIndex_middleOfLargerList_placesElementCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        customVector.add(40);
        customVector.add(3, 50);
        assertEquals(50, customVector.get(3));
    }

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertThrows(NullPointerException.class, () -> customVector.contains(null));
    }

    @Test
    public void contains_missingItem_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertFalse(customVector.contains(100));
    }

    @Test
    public void contains_existingItem_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(10);
        customVector.add(20);
        customVector.add(30);
        assertTrue(customVector.contains(20));
    }

    @Test
    public void set_negativeIndex_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.set(-1, 40));
    }

    @Test
    public void set_indexBeyondSize_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.set(4, 40));
    }

    @Test
    public void set_indexZero_updatesValueAndReturnsOldValue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals(1, customVector.set(0, 10));
        assertEquals(10, customVector.get(0));
    }

    @Test
    public void set_indexOne_updatesValueAndReturnsOldValue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals(2, customVector.set(1, 10));
        assertEquals(10, customVector.get(1));
    }

    @Test
    public void set_indexTwo_updatesValueAndReturnsOldValue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        customVector.add(4);
        assertEquals(3, customVector.set(2, 10));
        assertEquals(10, customVector.get(2));
    }

    @Test
    public void set_indexThree_updatesValueAndReturnsOldValue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        customVector.add(4);
        assertEquals(4, customVector.set(3, 10));
        assertEquals(10, customVector.get(3));
    }

    @Test
    public void toArray_emptyVector_returnsEmptyArray() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertEquals(0, customVector.toArray().length);
    }

    @Test
    public void toArray_populatedVector_returnsCorrectArray() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertArrayEquals(new Integer[] { 1, 2, 3 }, customVector.toArray());
    }

    @Test
    public void toArray_withExactSizeParameter_returnsCorrectArray() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertArrayEquals(new Integer[] {1, 2, 3}, customVector.toArray(new Integer[0]));
    }

    @Test
    public void toArray_withSmallerArrayParameter_returnsCorrectArray() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertArrayEquals(new Integer[] {1, 2, 3}, customVector.toArray(new Integer[2]));
    }

    @Test
    public void toArray_withLargerArrayParameter_padsWithNulls() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertArrayEquals(new Integer[] {1, 2, 3, null, null}, customVector.toArray(new Integer[5]));
    }

    @Test
    public void addAll_nullCollection_throwsNullPointerException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertThrows(NullPointerException.class, () -> customVector.addAll(null));
    }

    @Test
    public void addAll_emptyCollection_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertFalse(customVector.addAll(new ArrayList<>()));
    }

    @Test
    public void addAllAtIndex_emptyCollection_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2));
        Collection<Integer> collection = List.of();
        assertFalse(customVector.addAll(1, collection));
    }

    @Test
    public void addAllAtIndex_anotherCustomVector_insertsAtBeginning() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(10, 20));
        CustomVector<Integer> toInsert = new CustomVector<>(List.of(1, 2));
        customVector.addAll(0, toInsert);
        assertEquals(List.of(1, 2, 10, 20), List.copyOf(customVector));
    }

    @Test
    public void addAllAtIndex_validCollection_insertsCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2));
        Collection<Integer> collection = List.of(3);
        assertTrue(customVector.addAll(1, collection));
        assertEquals(1, customVector.get(0));
        assertEquals(3, customVector.get(1));
        assertEquals(2, customVector.get(2));
    }

    @Test
    public void addAllAtIndex_multipleElements_updatesListCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2));
        Collection<Integer> collection = List.of(3);
        assertTrue(customVector.addAll(1, collection));
        assertEquals(1, customVector.get(0));
        assertEquals(3, customVector.get(1));
        assertEquals(2, customVector.get(2));
    }

    @Test
    public void addAll_multipleElements_appendsToVector() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        Collection<Integer> collection = List.of(4, 5, 6);
        assertTrue(customVector.addAll(collection));
        assertEquals(1, customVector.get(0));
        assertEquals(2, customVector.get(1));
        assertEquals(3, customVector.get(2));
        assertEquals(4, customVector.get(3));
        assertEquals(5, customVector.get(4));
        assertEquals(6, customVector.get(5));
    }

    @Test
    public void equals_differentVectors_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);

        CustomVector<Integer> customVectorTwo = new CustomVector<>();
        customVectorTwo.add(4);
        customVectorTwo.add(5);
        customVectorTwo.add(6);
        assertNotEquals(customVector, customVectorTwo);
    }

    @Test
    public void equals_identicalVectors_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);

        CustomVector<Integer> customVectorTwo = new CustomVector<>();
        customVectorTwo.add(1);
        customVectorTwo.add(2);
        customVectorTwo.add(3);
        assertEquals(customVector, customVectorTwo);
    }

    @Test
    public void removeObject_missingElement_returnsFalse() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertFalse(customVector.remove("d"));
    }

    @Test
    public void removeObject_existingElement_returnsTrueAndRemovesItem() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertTrue(customVector.remove("b"));
        assertEquals("a", customVector.get(0));
        assertEquals("c", customVector.get(1));
    }

    @Test
    public void indexOf_missingElement_returnsMinusOne() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertEquals(-1, customVector.indexOf("d"));
    }

    @Test
    public void indexOf_existingElements_returnsCorrectIndices() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertEquals(0, customVector.indexOf("a"));
        assertEquals(1, customVector.indexOf("b"));
        assertEquals(2, customVector.indexOf("c"));
    }

    @Test
    public void lastIndexOf_missingElement_returnsMinusOne() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertEquals(-1, customVector.lastIndexOf("d"));
    }

    @Test
    public void lastIndexOf_singleOccurrence_returnsCorrectIndex() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertEquals(0, customVector.lastIndexOf("a"));
    }

    @Test
    public void lastIndexOf_secondOccurrence_returnsCorrectIndex() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("c");
        assertEquals(1, customVector.lastIndexOf("b"));
    }

    @Test
    public void lastIndexOf_multipleOccurrences_returnsLastIndex() {
        CustomVector<String> customVector = new CustomVector<>();
        customVector.add("a");
        customVector.add("b");
        customVector.add("a");
        customVector.add("c");
        customVector.add("a");
        customVector.add("d");
        assertEquals(4, customVector.lastIndexOf("a"));
    }

    @Test
    public void addAllAtIndex_insertsCollectionInMiddle() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        Collection<Integer> collection = List.of(4, 5, 6);
        assertTrue(customVector.addAll(1, collection));
        assertEquals(1, customVector.get(0));
        assertEquals(4, customVector.get(1));
        assertEquals(5, customVector.get(2));
        assertEquals(6, customVector.get(3));
        assertEquals(2, customVector.get(4));
        assertEquals(3, customVector.get(5));
    }

    @Test
    public void toString_populatedVector_returnsFormattedString() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals("CustomVector{size=3, list=[1, 2, 3]}", customVector.toString());
    }

    @Test
    public void toString_emptyVector_returnsEmptyBracesString() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertEquals("CustomVector{size=0, list=[]}", customVector.toString());
    }

    @Test
    public void clear_emptyVector_remainsEmpty() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.clear();
        assertTrue(customVector.isEmpty());
    }

    @Test
    public void clear_populatedVector_resultsInEmptyVector() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2));
        customVector.clear();
        assertTrue(customVector.isEmpty());
    }

    @Test
    public void clone_populatedVector_returnsEqualCopy() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.add(1);
        customVector.add(2);
        customVector.add(3);
        assertEquals(customVector, customVector.clone());
    }

    @Test
    public void containsAll_subsetOfElements_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        List<Integer> collection = IntStream.of(5, 10, 20).boxed().toList();
        IntStream.range(0, 33).forEach(customVector::add);
        assertTrue(customVector.containsAll(collection));
    }

    @Test
    public void containsAll_missingElement_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        List<Integer> collection = IntStream.of(5, 10, 200).boxed().toList();
        IntStream.range(0, 33).forEach(customVector::add);
        assertFalse(customVector.containsAll(collection));
    }

    @Test
    public void containsAll_withSetCollection_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        Set<Integer> collection = new HashSet<>(List.of(1, 2, 3));
        assertTrue(customVector.containsAll(collection));
    }

    @Test
    public void containsAll_nullCollection_throwsNullPointerException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 33).forEach(customVector::add);
        assertThrows(NullPointerException.class, () -> customVector.containsAll(null));
    }

    @Test
    public void subList_fromIndexNegative_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.subList(-1, 10));
    }

    @Test
    public void subList_toIndexExceedsSize_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.subList(0, 6));
    }

    @Test
    public void subList_equalIndices_returnsEmptyList() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        List<Integer> empty = customVector.subList(3, 3);
        assertTrue(empty.isEmpty());
    }

    @Test
    public void subList_fromIndexGreaterThanToIndex_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.subList(2, 1));
    }

    @Test
    public void subList_validRange_returnsExpectedSubList() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 10).mapToObj(i -> i * 10).forEach(customVector::add);
        CustomVector<Integer> expected = new CustomVector<>();
        IntStream.range(2, 8).mapToObj(i -> i * 10).forEach(expected::add);
        List<Integer> subList = customVector.subList(2, 8);
        assertEquals(subList, expected);
    }

    @Test
    public void removeAll_nullCollection_throwsNullPointerException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        assertThrows(NullPointerException.class, () -> customVector.removeAll(null));
    }

    @Test
    public void removeAll_emptyCollection_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        assertFalse(customVector.removeAll(new ArrayList<>()));
    }

    @Test
    public void removeAll_matchingElements_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        Collection<Integer> items = IntStream.range(0, 3).mapToObj(i -> i * 10).toList();
        assertTrue(customVector.removeAll(items));
    }

    @Test
    public void removeAll_withSetCollection_removesElementsCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        Set<Integer> setToRemove = Set.of(0, 10);
        assertTrue(customVector.removeAll(setToRemove));
        assertEquals(List.of(20, 30, 40), customVector);
    }

    @Test
    public void removeAll_partialMatch_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        Collection<Integer> items = IntStream.range(2, 6).mapToObj(i -> i * 10).toList();
        assertTrue(customVector.removeAll(items));
    }

    @Test
    public void removeAll_withGaps_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 5).mapToObj(i -> i * 10).forEach(customVector::add);
        Collection<Integer> items = IntStream.of(0, 30, 10).boxed().toList();
        assertTrue(customVector.removeAll(items));
    }

    @Test
    public void addAllAtIndex_middleSingleElement_maintainsIntegrity() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(10, 20, 30));
        assertTrue(customVector.addAll(1, List.of(99)));
        assertEquals(4, customVector.size());
        assertListEquals(customVector, 10, 99, 20, 30);
        assertEquals(30, customVector.get(3));
    }

    @Test
    public void addAllAtIndex_middleMultipleElements_maintainsIntegrity() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.addAll(List.of(1, 2, 3, 4, 5));
        customVector.addAll(2, List.of(100, 200, 300));
        assertEquals(8, customVector.size());
        assertListEquals(customVector, 1, 2, 100, 200, 300, 3, 4, 5);
        assertEquals(300, customVector.get(4));
        assertEquals(3, customVector.get(5));
        assertEquals(5, customVector.get(7));
    }

    @Test
    public void addAllAtIndex_positionOne_doesNotCorruptList() {
        CustomVector<String> customVector = new CustomVector<>(List.of("A", "B", "C", "D", "E"));
        customVector.addAll(1, List.of("X", "Y"));
        assertEquals(7, customVector.size());
        assertEquals("A", customVector.get(0));
        assertEquals("X", customVector.get(1));
        assertEquals("Y", customVector.get(2));
        assertEquals("B", customVector.get(3));
        assertEquals("E", customVector.get(6));
    }

    @Test
    public void addAllAtIndex_middle_iterationContinuesSuccessfully() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3, 4, 5, 6, 7));
        customVector.addAll(3, List.of(100, 200));
        assertEquals(9, customVector.size());
    }

    @Test
    public void addAllAtIndex_nearTail_reachesOriginalTail() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3, 4));
        customVector.addAll(3, List.of(-1, -2));
        assertEquals(6, customVector.size());
        assertEquals(-2, customVector.get(4));
        assertEquals(4, customVector.get(5));
    }

    @Test
    public void addAllAtIndex_sameInstanceAtBeginning_doesNotFail() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        customVector.addAll(0, customVector);
    }

    @Test
    public void addAll_bulkInsertMultiplePositions_succeeds() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.addAll(List.of(1, 2, 3));
        customVector.addAll(1, List.of(10, 11));
        customVector.addAll(0, List.of(99));
        customVector.addAll(customVector.size(), List.of(88));
        assertEquals(List.of(99, 1, 10, 11, 2, 3, 88), List.copyOf(customVector));
    }

    @Test
    public void mixedOperations_indexedAndBulk_succeeds() {
        CustomVector<Integer> customVector = new CustomVector<>();
        customVector.addAll(List.of(1, 2, 3));
        customVector.add(1, 99);
        customVector.addAll(2, List.of(100, 101));
        customVector.remove(0);
        customVector.set(3, 777);
        assertEquals(List.of(99, 100, 101, 777, 3), List.copyOf(customVector));
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertEquals(customVector, customVector);
    }

    @Test
    public void equals_nonListType_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertNotEquals(customVector, new HashSet<>());
    }

    @Test
    public void equals_differentSizes_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        CustomVector<Integer> customVectorTwo = new CustomVector<>(List.of(1, 2));
        assertNotEquals(customVector, customVectorTwo);
    }

    @Test
    public void equals_matchingVectors_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        CustomVector<Integer> customVectorTwo = new CustomVector<>(List.of(1, 2, 3));
        assertEquals(customVector, customVectorTwo);
    }

    @Test
    public void equals_bothEmpty_returnsTrue() {
        CustomVector<Integer> customVector = new CustomVector<>();
        CustomVector<Integer> customVectorTwo = new CustomVector<>();
        assertEquals(customVector, customVectorTwo);
    }

    @Test
    public void equals_differentLengths_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        CustomVector<Integer> customVectorTwo = new CustomVector<>(List.of(1, 2, 3, 4));
        assertNotEquals(customVector, customVectorTwo);
    }

    @Test
    public void equals_sameSizeDifferentContent_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        CustomVector<Integer> customVectorTwo = new CustomVector<>(List.of(1, 2, 4));
        assertNotEquals(customVector, customVectorTwo);
    }

    @Test
    public void retainAll_nullCollection_throwsNullPointerException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertThrows(NullPointerException.class, () -> customVector.retainAll(null));
    }

    @Test
    public void retainAll_emptyCollection_emptiesVector() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        Collection<Integer> empty = new ArrayList<>();
        assertTrue(customVector.retainAll(empty));
        assertFalse(customVector.contains(1));
        assertFalse(customVector.contains(2));
        assertFalse(customVector.contains(3));
        assertTrue(customVector.isEmpty());
    }

    @Test
    public void retainAll_emptyVectorWithEmptyCollection_returnsFalse() {
        CustomVector<Integer> customVector = new CustomVector<>();
        Collection<Integer> empty = new ArrayList<>();
        assertFalse(customVector.retainAll(empty));
    }

    @Test
    public void retainAll_withList_retainsSpecifiedElements() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        Collection<Integer> retain = new ArrayList<>(List.of(1, 2));
        assertTrue(customVector.retainAll(retain));
        assertTrue(customVector.contains(1));
        assertTrue(customVector.contains(2));
        assertFalse(customVector.contains(3));
    }

    @Test
    public void retainAll_withSet_retainsSpecifiedElements() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        Collection<Integer> retain = new HashSet<>(List.of(1, 2));
        assertTrue(customVector.retainAll(retain));
        assertTrue(customVector.contains(1));
        assertTrue(customVector.contains(2));
        assertFalse(customVector.contains(3));
    }

    @Test
    public void hashCode_emptyVector_returnsOne() {
        assertEquals(1, new CustomVector<>().hashCode());
    }

    @Test
    public void hashCode_matchesStandardJavaList() {
        List<Integer> custom = new CustomVector<>(List.of(1, 2, 3));
        List<Integer> standard = new CustomVector<>(List.of(1, 2, 3));
        assertEquals(standard.hashCode(), custom.hashCode());
    }

    @Test
    public void iterator_emptyVectorNext_throwsNoSuchElementException() {
        CustomVector<Integer> customVector = new CustomVector<>();
        assertThrows(NoSuchElementException.class, () -> customVector.iterator().next());
    }

    @Test
    public void listIterator_populatedVector_hasValidNext() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertTrue(customVector.listIterator().hasNext());
    }

    @Test
    public void listIterator_negativeIndex_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.listIterator(-1));
    }

    @Test
    public void listIterator_indexBeyondSize_throwsIndexOutOfBoundsException() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> customVector.listIterator(4));
    }

    @Test
    public void listIterator_start_hasNoPrevious() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        assertFalse(customVector.listIterator().hasPrevious());
    }

    @Test
    public void listIterator_advanceAndRetreat_worksCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        ListIterator<Integer> listIterator = customVector.listIterator();
        assertEquals(1, listIterator.next());
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
        assertEquals(2, listIterator.previous());
    }

    @Test
    public void listIterator_atIndex_traversesCorrectly() {
        CustomVector<Integer> customVector = new CustomVector<>(List.of(1, 2, 3));
        ListIterator<Integer> listIterator = customVector.listIterator(1);
        assertEquals(2, listIterator.next());
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
        assertEquals(3, listIterator.previous());
    }

    @Test
    public void reduce_triggeredByBulkRemove_shrinksCapacity() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 99).forEach(customVector::add);
        IntStream.range(0, 50).map(i -> 0).forEach(customVector::remove);
        assertEquals(50, customVector.get(0));
    }

    @Test
    public void reduce_triggeredByCollectionRemoveAll_shrinksCapacity() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 99).forEach(customVector::add);
        List<Integer> toRemove = IntStream.range(0, 50).boxed().toList();
        customVector.removeAll(toRemove);
        assertEquals(50, customVector.get(0));
    }

    @Test
    public void reduce_triggeredByCollectionRetainAll_shrinksCapacity() {
        CustomVector<Integer> customVector = new CustomVector<>();
        IntStream.range(0, 99).forEach(customVector::add);
        List<Integer> toRetain = IntStream.range(0, 50).boxed().toList();
        customVector.retainAll(toRetain);
        assertEquals(0, customVector.get(0));
    }

    private void assertListEquals(CustomVector<?> list, Integer... expected) {
        assertEquals(expected.length, list.size());
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], list.get(i));
    }
}