package moviebuddy;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;


@Configuration
public class MovieBuddyFactory {
	
	/*
	 * 1. Singleton 스코프: 스프링 컨테이너는 singleton 스코프로 정의된 빈에 대해 하나의 인스턴스만을 생성하며, 이후 같은 빈을 요청하면 항상 동일한 인스턴스를 반환한다.
	 * 	따라서 같은 컨텍스트에서 singleton 스코프의 빈을 여러 번 요청하여 얻은 인스턴스를 서로 비교하면 동일하다는 것을 알 수 있다.
	 */
	
	@Bean
	public MovieFinder movieFinder() {
		return new MovieFinder(new CsvMovieReader());
	}  
		
	/*
	 * 2. Prototype 스코프: prototype 스코프로 정의된 빈을 요청할 때마다 새로운 인스턴스가 생성된다.  
	 * 	따라서 같은 컨텍스트에서 prototype 스코프의 빈을 여러 번 요청하여 얻은 인스턴스를 서로 비교하면 다르다는 것을 알 수 있다.
	 * */
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MovieFinder moiveFinder(MovieReader movieReader) {
		return new MovieFinder(movieReader);
	}
}
