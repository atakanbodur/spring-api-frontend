package tr.edu.ozyegin.springapifrontend.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ozyegin.springapifrontend.model.Song;


public interface SongRepository extends JpaRepository<Song, Integer> {
	@Override
	Optional<Song> findById(Integer integer);

	public Set<Song> findSongByAlbumArtistId(int id);
	
	
	public Set<Song> findSongByArtistId(int id);

	@EntityGraph(attributePaths = {"artist"})
	public List<Song> findSongsByName(String name);
}
