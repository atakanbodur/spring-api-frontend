package tr.edu.ozyegin.springapifrontend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ozyegin.springapifrontend.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    @Override
    <S extends Playlist> S save(S entity);

    Optional<Playlist> findByName(String name);

    @EntityGraph(attributePaths = {"songs"})
    List<Playlist> findAll();
}
