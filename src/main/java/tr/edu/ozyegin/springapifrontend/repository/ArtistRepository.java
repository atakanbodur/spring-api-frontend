package tr.edu.ozyegin.springapifrontend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ozyegin.springapifrontend.model.Artist;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
