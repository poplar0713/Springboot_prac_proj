package moviebuddy.data;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.AopTestUtils;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;

import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;

@ActiveProfiles(MovieBuddyProfile.XML_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
@TestPropertySource(properties = "movie.metadata=movie_metadata.xml")
public class XmlMovieReaderTest {

	@Autowired
	MovieReader movieReader;
	
	@Test
	void NotEmpty_LoadMovies() {
		List<Movie> movies = movieReader.loadMovies();
		Assertions.assertEquals(1375, movies.size());
		//MovieFinderTest.assertEquals(1374, movies.size()); // 정상값 1375
	}
	
	@Test
	void Check_MovieReader_Type() {
		Assertions.assertTrue(AopUtils.isAopProxy(movieReader));
		
		MovieReader target = AopTestUtils.getTargetObject(movieReader);
		Assertions.assertTrue(XmlMovieReader.class.isAssignableFrom(target.getClass()));
	}
}
 