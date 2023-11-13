package moviebuddy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import moviebuddy.data.CachingMovieReader;
import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;

//import moviebuddy.domain.CsvMovieReader;
//import moviebuddy.domain.MovieFinder;
//import moviebuddy.domain.MovieReader;


@Configuration
@PropertySource("application.properties")
@ComponentScan(basePackages = { "moviebuddy" })
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("moviebuddy");
		
		return marshaller;
	}
	
	
	/*
	 * 1. Singleton 스코프: 스프링 컨테이너는 singleton 스코프로 정의된 빈에 대해 하나의 인스턴스만을 생성하며, 이후 같은 빈을 요청하면 항상 동일한 인스턴스를 반환한다.
	 * 	따라서 같은 컨텍스트에서 singleton 스코프의 빈을 여러 번 요청하여 얻은 인스턴스를 서로 비교하면 동일하다는 것을 알 수 있다.
	 */
		
	/*
	 * 2. Prototype 스코프: prototype 스코프로 정의된 빈을 요청할 때마다 새로운 인스턴스가 생성된다.  
	 * 	따라서 같은 컨텍스트에서 prototype 스코프의 빈을 여러 번 요청하여 얻은 인스턴스를 서로 비교하면 다르다는 것을 알 수 있다.
	 * */
	
//	@Bean
////	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	public MovieFinder moiveFinder(MovieReader movieReader) {
//		return new MovieFinder(movieReader);
//	}
//	
	//스프링의 Bean 구성 정보로 사용 목적 
	@Configuration
	static class DomainModuleConfig {
//		@Bean
//		public MovieFinder movieFinder(MovieReader movieReader) {
//			return new MovieFinder(movieReader);
//		}
	}
	
	@Bean
	public CacheManager caffeineCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS));
		
		return cacheManager;
	}
	
	@Configuration
	static class DataSourceModuleConfig {
		
//		private final Environment environment;
//		
//		@Autowired
//		public DataSourceModuleConfig(Environment environment) {
//			this.environment = environment;
//		}
//		
//		@Profile(MovieBuddyProfile.CSV_MODE)
//		@Bean
//		public CsvMovieReader csvMovieReader () {
//			CsvMovieReader movieReader = new CsvMovieReader();
//			
//			//Application 외부에서 설정 정보를 읽어, 메타데이터 위치 설정하기.			
//			//movieReader.setMetadata(environment.getProperty("movie.metadata"));
//			return movieReader;
//		}
//		
//		@Profile(MovieBuddyProfile.XML_MODE)
//		@Bean
//		public XmlMovieReader xmlMovieReader(Unmarshaller unmarshaller) {
//			XmlMovieReader movieReader = new XmlMovieReader(unmarshaller);
//			//movieReader.setMetadata(environment.getProperty("movie.metadata"));
//			return movieReader;
//		}
		
		@Primary
		@Bean
		public MovieReader cachingMovieReader(CacheManager cacheManager, MovieReader target) {
			return new CachingMovieReader(cacheManager, target);
		}
		
	}
}