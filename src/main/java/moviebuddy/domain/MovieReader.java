 package moviebuddy.domain;

import java.util.List;

public interface MovieReader {
	
	/**
     * 영화 메타데이터를 읽어 저장된 영화 목록을 불러온다.
     * 
     * @return 불러온 영화 목록
     */
	
	public List<Movie> loadMovies();
}
