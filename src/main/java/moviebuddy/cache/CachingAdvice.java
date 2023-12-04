package moviebuddy.cache;

import java.util.Objects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.ClassUtils;

import moviebuddy.domain.MovieReader;

/*
 * 코드의 역할 :
 * 1. 캐시 확인 : 메서드가 호출될 때 먼저 CacheManager를 통해 적절한 캐시를 찾고, 현재 호출된 메서드의 이름을 키로 사용하여 캐시된 값을 확인한다.
 * 2. 캐시된 값 반환 :캐시된 값이 존재하면, 그 값을 즉시 반환.
 * 3. 대상 메서드 실행 : 캐시된 값이 없으면, 실제 대상 객체의 메서드를 실행('invocation.proceed()')
 * 4. 결과 캐싱 : 대상 메서드의 실행결과를 캐시에 저장한다. 
 * 5. 결과 반환 : 실행 결과를 반환한다. 
 * 
 * */

public class CachingAdvice implements MethodInterceptor {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final CacheManager cacheManager;
	
	public CachingAdvice(CacheManager cacheManager) {
		this.cacheManager = Objects.requireNonNull(cacheManager);
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 캐시된 데이터가 존재하면 즉시 반환처리.
		Cache cache = cacheManager.getCache(invocation.getThis().getClass().getName());
		Object cachedValue = cache.get(invocation.getMethod().getName(), Object.class);
		
		if(Objects.nonNull(cachedValue)) {
			log.info("returns cached data. [{}]", invocation);
			return cachedValue;
		}		
		
		// 캐시된 데이터없으면 대상객체에 명령 위임, 반환된 값을 캐시에 저장
		cachedValue = invocation.proceed();
		cache.put(invocation.getMethod().getName(), cachedValue);
		log.info("caching return value. [{}]", invocation);
		
		return cachedValue;
	}
	
}
