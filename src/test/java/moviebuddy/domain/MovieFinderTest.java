package moviebuddy.domain;

import java.util.List;

import moviebuddy.MovieBuddyFactory;

/**
 * @author springrunner.kr@gmail.com
 */
public class MovieFinderTest {

	public static void main(String[] args) {
		MovieBuddyFactory movieBuddyFactory = new MovieBuddyFactory();
		MovieFinder movieFinder = movieBuddyFactory.movieFinder();
		
		List<Movie> result = movieFinder.directedBy("Michael Bay");
		assertEquals(3, result.size()); // 기댓값과 일치하는가?

        result = movieFinder.releasedYearBy(2015);
        assertEquals(225, result.size());
	}
	
	static void assertEquals(long expected, long actual) {
		if (expected != actual) {
			throw new RuntimeException(String.format("actual(%d) is different from the expected(%d)", actual, expected));			
		}
	}
	
}
