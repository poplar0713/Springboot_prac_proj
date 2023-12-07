package moviebuddy;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdkLocaleTests {
	
	final Logger log = LoggerFactory.getLogger(getClass());
	
	@Test
	void printLocales() {
		logging(Locale.KOREA);
		logging(Locale.CHINA);
		logging(Locale.US);
		logging(Locale.JAPAN);
		logging(Locale.FRANCE);
		
		logging(Locale.getDefault());
	}
	
	void logging(Locale locale) {
		log.info("Locale: {}", locale.toString());
		log.info("Language: {} DisplayLanguage: {}", locale.getLanguage(), locale.getDisplayLanguage());
		log.info("Country: {}, DisplayCountry: {}", locale.getCountry(), locale.getDisplayCountry());
		log.debug("=========================================");
	}

}
