package moviebuddy.data;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.ApplicationContextException;

public class AbstractFileSystemMovieReader {

	private String metadata;

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		// 빈 초기화 될때 동작정의 
		URL metadataUrl = URLClassLoader.getSystemResource(metadata);
		if (Objects.isNull(metadataUrl)) {
			throw new FileNotFoundException(metadata);
		}
		
		if (Files.isReadable(Path.of(metadataUrl.toURI())) == false) {
			throw new ApplicationContextException(String.format("cannot read to meatadata. [%s]", metadata));
		}
	}

	@PreDestroy
	public void destroy() throws Exception {
			// 빈이 소멸될때의 작업 
	//		log.info("Destoryed Bean");
		}

}