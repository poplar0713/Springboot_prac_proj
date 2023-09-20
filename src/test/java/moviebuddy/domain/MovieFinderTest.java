package moviebuddy.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import moviebuddy.MovieBuddyFactory;

public class MovieFinderTest {
	
	final MovieBuddyFactory movieBuddyFactory = new MovieBuddyFactory();
	final MovieFinder movieFinder = movieBuddyFactory.movieFinder();
	
	@Test
	void NoEmpty_directedBy() {
		List<Movie> movies = movieFinder.directedBy("Michael Bay");
		Assertions.assertEquals(3, movies.size());
	}
	
	@Test
	void NotEmpty_ReleasedYearBy() {
		List<Movie> movies = movieFinder.releasedYearBy(2015);
		Assertions.assertEquals(225, movies.size());
	}

//	public static void main(String[] args) {
//		
//		MovieFinder movieFinder = movieBuddyFactory.movieFinder();
//		
//		List<Movie> result = movieFinder.directedBy("Michael Bay");
//		assertEquals(3, result.size()); // 기댓값과 일치하는가?
//
//        result = movieFinder.releasedYearBy(2015);
//        assertEquals(225, result.size());
//	}
//	
//	static void assertEquals(long expected, long actual) {
//		if (expected != actual) {
//			throw new RuntimeException(String.format("actual(%d) is different from the expected(%d)", actual, expected));			
//		}
//	}
	
}
