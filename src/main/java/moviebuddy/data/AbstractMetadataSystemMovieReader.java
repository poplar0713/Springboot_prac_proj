package moviebuddy.data;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class AbstractMetadataSystemMovieReader implements ResourceLoaderAware {

	private String metadata;
	private ResourceLoader resourceLoader;

	public String getMetadata() {
		return metadata;
	}

	@Value("${movie.metadata}")
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		// 빈 초기화 될때 동작정의
		Resource resource = getMetadataResource();
		if(!resource.exists()) {
			throw new FileNotFoundException(metadata);
		}
		
		if(!resource.isReadable()) {
			throw new ApplicationContextException(String.format("cannot read to meatadata. [%s]", metadata));
		}
	}
	
	
	@PreDestroy
	public void destroy() throws Exception {
	// 빈이 소멸될때의 작업 
	//	log.info("Destoryed Bean");
	}

	public Resource getMetadataResource() {
		return resourceLoader.getResource(getMetadata());
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}