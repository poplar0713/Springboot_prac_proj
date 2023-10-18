package moviebuddy.data;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import moviebuddy.data.JaxbMovieReader;
import moviebuddy.domain.Movie;

@SpringJUnitConfig(JaxbMovieReader.class)
public class JaxbMovieReaderTest {

	@Autowired
	JaxbMovieReader movieReader;
	
	@Test
	void NotEmpty_LoadMovies() {
		List<Movie> movies = movieReader.loadMovies();
		Assertions.assertEquals(1375, movies.size());
		//MovieFinderTest.assertEquals(1374, movies.size()); // 정상값 1375
	}
}
 