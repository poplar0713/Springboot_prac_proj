package moviebuddy.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JaxbMovieReaderTest {

	@Test
	void NotEmpty_LoadMovies() {
		JaxbMovieReader movieReader = new JaxbMovieReader();
		
		List<Movie> movies = movieReader.loadMovies();
		Assertions.assertEquals(1374, movies.size());
		//MovieFinderTest.assertEquals(1374, movies.size()); // 정상값 1375
	}
}
