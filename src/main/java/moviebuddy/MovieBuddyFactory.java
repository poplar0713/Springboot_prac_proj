package moviebuddy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
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

import moviebuddy.cache.CachingAdvice;
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

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
		//proxy를 직접 등록해 주지 않아도 캐시라는 부가기능을 Advisor에 담아서 DefaultAdvisorAutoProxyCreator가 proxy를 자동으로 구성해서 스프링컨테이너에 등록을 해줄것이다
	}

	@Bean
	public Advisor cachingAdvisor(CacheManager cacheManager) {
		Advice advice = new CachingAdvice(cacheManager);

		// Advisor = PointCut(대상 선정 알고리즘) + Advice(부가기능)
		return new DefaultPointcutAdvisor(advice);
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
		
//		@Primary
//		@Bean
//		public ProxyFactoryBean cachingMovieReaderFactory(ApplicationContext applicationContext) {
//			MovieReader target = applicationContext.getBean(MovieReader.class);
//			CacheManager cahceManager = applicationContext.getBean(CacheManager.class);
//
//			ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//			proxyFactoryBean.setTarget(target);
//
//			// 이 클래스 proxy는 CGLIB라는 바이트코드 생성 라이브러리를 통해 대상 객체타입을 상속해서 서브 클래스로 만들어 이를 proxy로 사용한다.
//			// 즉, 서브클래스도 자신이 상속한 대상 객체와 같은 타입이니까 클라언트에게 의존 관계 주입이 가능하다는 원리를 이용
//			// 이 클래스 proxy는 두가지 제약이 있는데, final 클래스와 final 메소드에는 적용이 안되고 같은 대상 클래스 타입의 빈이 두개가 만들어지기 때문에 대상 클래스 생성자가 두번 호출된다는 점이다.
//
//			// 이 방식은 매우 부자연스러운데, 프락시를 사용하기 위해서 동적으로 클래스를 상속할 뿐 더러, 이 상속받은 객체의 public 메소드를 모두 오버라이드 해서
//			// 프락시 기능으로 바꿔치는 방식으로 동작하기 때문이다.
//
//			// 스프링은 인터페이스가 없이 개발된 레거시코드나, 외부에서 개발된 인터페이스 없는 라이브러리들을 이 proxy 기법을 적용할 수 있도록 지원해 주기 위해서
//			// 클래스 proxy를 지원한다.
////			proxyFactoryBean.setProxyTargetClass(true); // 클래스 프록시 활성화 코드
//
//
//			proxyFactoryBean.addAdvice(new CachingAdvice(cahceManager));
//
//			return proxyFactoryBean;
//
//		}
	}
}