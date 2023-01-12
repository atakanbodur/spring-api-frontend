package tr.edu.ozyegin.springapifrontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ozyegin.springapifrontend.dto.PlayListDto;
import tr.edu.ozyegin.springapifrontend.dto.SongDto;
import tr.edu.ozyegin.springapifrontend.model.Playlist;
import tr.edu.ozyegin.springapifrontend.model.Song;
import tr.edu.ozyegin.springapifrontend.service.PlaylistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping(value = "/api")
@CrossOrigin
public class Controller {
    @Autowired
    PlaylistService service;

    @GetMapping(value = "/searchSong/{name}")
    ResponseEntity searchSongBySongNameOrArtistName(@PathVariable String name) {
        List<SongDto> songs = service.findSongByNameOrArtistName(name);
        if(songs.size()>0)
            return ResponseEntity.ok().body(songs);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping(value = "/createPlaylist")
    ResponseEntity createPlaylist(@RequestBody PlayListDto playlist)  {
        try {
            Playlist result = service.createPlayList(playlist);
            if (result.getName().equals(playlist.name)) return ResponseEntity.ok().body(result);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/getAllPlaylists")
    ResponseEntity getAllPlaylists() throws JsonProcessingException {
        List<Playlist> result = service.getAllPlaylists();
        if (result.size() != 0)
            return ResponseEntity.ok().body(result);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
