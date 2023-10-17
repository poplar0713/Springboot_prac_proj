package moviebuddy.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import moviebuddy.MovieBuddyFactory;

@ExtendWith(SpringExtension.class) // JUnit이 테스트 실행전략을 확장할 때 사
@ContextConfiguration(classes = MovieBuddyFactory.class) // 테스트를 필요로 하는 스프링 컨테이너를 구성, 관리 
public class MovieFinderTest {
	
//	final ApplicationContext applicationContext = 
//			new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
	@Autowired
	MovieFinder movieFinder;
	
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
	
//	final MovieBuddyFactory movieBuddyFactory = new MovieBuddyFactory();
//	final MovieFinder movieFinder = movieBuddyFactory.movieFinder();

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
