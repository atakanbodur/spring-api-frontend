package tr.edu.ozyegin.springapifrontend.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ozyegin.springapifrontend.model.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

        @EntityGraph(attributePaths = {"songs", "songs.artist"})
        List<Album> findAllByArtist_Name(String name);
}
