package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import disk_store.OrdIndex;

class OrdIndexTest {
	
	OrdIndex idx;
	
	@BeforeEach
	void init() {
		// create the index
		idx = new OrdIndex();
		
		// insert some elements
		idx.insert(1, 1);
		idx.insert(1, 2);
		idx.insert(2, 1);
		idx.insert(2, 2);
		idx.insert(2, 3);
		idx.insert(3, 1);
		idx.insert(4, 2);
	}
	
	@Test
	void testInsert() {
		assertTrue(idx.size() == 7);
	}
	
	@Test
	void testLookup() {
		List<Integer> result = idx.lookup(1);
		List<Integer> expected = Arrays.asList(1,2);
		assertTrue(result.equals(expected));
		
		result = idx.lookup(2);
		expected = Arrays.asList(1,2,3);
		assertTrue(result.equals(expected));
		
		result = idx.lookup(4);
		expected = Arrays.asList(2);
		assertTrue(result.equals(expected));
		
		result = idx.lookup(6);
		assertTrue(result.size() == 0);
	}
	
	@Test
	void testDelete() {
		idx.delete(2, 1);
		assertTrue(idx.size() == 6);
         
		idx.delete(1, 1);
		assertTrue(idx.size() == 5);
		
		idx.delete(1, 1);
		assertTrue(idx.size() == 5);
		
		idx.delete(2, 2);
		idx.delete(2, 3);
		List<Integer> result = idx.lookup(2);
		assertTrue(result.size() == 0);
	}
}
