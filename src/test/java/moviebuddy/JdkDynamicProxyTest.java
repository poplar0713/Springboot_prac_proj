package moviebuddy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import moviebuddy.data.CsvMovieReader;
import moviebuddy.domain.MovieReader;

public class JdkDynamicProxyTest {
	@Test
	void useDynamicProxy() throws Exception {
		CsvMovieReader movieReader = new CsvMovieReader();
		movieReader.setResourceLoader(new DefaultResourceLoader());
		movieReader.afterPropertiesSet();
		
		ClassLoader classLoader = JdkDynamicProxyTest.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { MovieReader.class };
		
		//MovieReader 안에 loadMovie() 가 실행되는 시간을 측정하기 위함 
		InvocationHandler handler = new PerformanceInvocationHandler(movieReader);
		
		
		MovieReader proxy = (MovieReader) Proxy.newProxyInstance(classLoader, interfaces, handler);
		proxy.loadMovies();
		proxy.loadMovies();
	}
	
	static class PerformanceInvocationHandler implements InvocationHandler {

		//final Logger log = LoggerFactory.getLogger(getClass());
		final Object target;
		
		PerformanceInvocationHandler(Object target) {
			this.target =  target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			long start = System.currentTimeMillis();
			Object result = method.invoke(target, args);
			long elapsed = System.currentTimeMillis() - start;
			
			//log.info("Excution {} method finished in {} ms", method.getName(), elapsed);
			
			return null;
		}
		
	}
}
