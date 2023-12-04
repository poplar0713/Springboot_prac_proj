package moviebuddy;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineTest {
	
	@Test
	void useCache() throws InterruptedException {
		Cache<String, Object> cache = Caffeine.newBuilder()
				.expireAfterWrite(200, TimeUnit.MILLISECONDS) //만료시간 설정 
				.maximumSize(100) // 최대 100개 까지의 테스트 객체 
				.build();
		
		String key = "cache test";
		Object value = new Object();
		
		Assertions.assertNull(cache.getIfPresent(key));
		
		cache.put(key, value);
		Assertions.assertEquals(value, cache.getIfPresent(key));
		
		TimeUnit.MILLISECONDS.sleep(100);
		Assertions.assertEquals(value, cache.getIfPresent(key));
		
		TimeUnit.MILLISECONDS.sleep(100);
		Assertions.assertNull(cache.getIfPresent(key));
		
	}
}
