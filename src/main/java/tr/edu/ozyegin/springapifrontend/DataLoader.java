package tr.edu.ozyegin.springapifrontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.edu.ozyegin.springapifrontend.model.Album;
import tr.edu.ozyegin.springapifrontend.model.Artist;
import tr.edu.ozyegin.springapifrontend.model.Playlist;
import tr.edu.ozyegin.springapifrontend.model.Song;
import tr.edu.ozyegin.springapifrontend.service.PlaylistService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {
    PlaylistService service;

    @Autowired
    public DataLoader(PlaylistService service) {
        this.service = service;
        loadData();
    }

    private void loadData() {
        Album album=new Album("Sözüm Meclisten Dışarı");
        Song song1=new Song("Gulpembe",312);
        Song song2=new Song("Dönence",351);
        Song song3=new Song("Hal Hal",215);
        Song song4=new Song("Gulpembe",0);


        song1.setAlbum(album);
        song2.setAlbum(album);
        song3.setAlbum(album);
        album.getSongs().add(song1);
        album.getSongs().add(song2);
        album.getSongs().add(song3);
        album.getSongs().add(song4);
        service.saveAlbum(album);


        Artist artist=new Artist("Barış Manço");
        service.saveArtist(artist);

        service.assignArtistToAlbum(artist.getId(), album.getId());

        service.assignArtistToSong(artist.getId(), song1.getId());
        service.assignArtistToSong(artist.getId(), song2.getId());
        service.assignArtistToSong(artist.getId(), song3.getId());
        service.assignArtistToSong(artist.getId(), song4.getId());

        String name="FirstLove";
        List<Song> songs=service.getAllSongs();
        List<Playlist> plList=new ArrayList<>();
        service.createPlayList(name,songs,plList);
    }
}
