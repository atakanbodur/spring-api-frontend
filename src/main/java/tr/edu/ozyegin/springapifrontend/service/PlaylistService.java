package tr.edu.ozyegin.springapifrontend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ozyegin.springapifrontend.dto.PlayListDto;
import tr.edu.ozyegin.springapifrontend.dto.SongDto;
import tr.edu.ozyegin.springapifrontend.model.Album;
import tr.edu.ozyegin.springapifrontend.model.Artist;
import tr.edu.ozyegin.springapifrontend.model.Playlist;
import tr.edu.ozyegin.springapifrontend.model.Song;
import tr.edu.ozyegin.springapifrontend.repository.AlbumRepository;
import tr.edu.ozyegin.springapifrontend.repository.ArtistRepository;
import tr.edu.ozyegin.springapifrontend.repository.PlaylistRepository;
import tr.edu.ozyegin.springapifrontend.repository.SongRepository;


@Service
public class PlaylistService {
    @Autowired
    SongRepository songRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void saveAlbum(Album album) {
        albumRepository.save(album);
    }

    public void saveSong(Song song) {
        songRepository.save(song);
    }


    @Transactional
    public void addSongToAlbum(int albumId, Song song) {
        Album album = albumRepository.findById(albumId).get();
        album.getSongs().add(song);
        song.setAlbum(album);
        albumRepository.save(album);
    }

    @Transactional
    public void assignArtistToAlbum(int artistId, int albumId) {
        Album album = albumRepository.findById(albumId).get();
        Artist artist = artistRepository.findById(artistId).get();
        album.setArtist(artist);
        artist.getAlbums().add(album);
        albumRepository.save(album);

    }

    @Transactional
    public void assignArtistToSong(int artistId, int songId) {
        Song song = songRepository.findById(songId).get();
        Artist artist = artistRepository.findById(artistId).get();
        song.setArtist(artist);
        artist.getSongs().add(song);
        songRepository.save(song);

    }

    @Transactional
    public void deleteAlbum(int albumId) {
        Album album = albumRepository.findById(albumId).get();
        for (Song song : album.getSongs()) {
            song.setAlbum(null);
            songRepository.save(song);
        }
        albumRepository.deleteById(albumId);
    }

    public void deleteSong(int songId) {
        songRepository.deleteById(songId);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public void createPlayList(String name, List<Song> songs, List<Playlist> playLists) {
        Playlist pl = new Playlist(name, songs, playLists);

        playlistRepository.save(pl);
    }

   @Transactional
    public Playlist createPlayList(PlayListDto listDto) {
        List<Song> songs = new ArrayList<>();
        for (SongDto s : listDto.songs) {
            Song song = songRepository.findById(s.id).get();
            songs.add(song);
        }
        Playlist pl = new Playlist(listDto.name, songs, new ArrayList<>());

       Playlist ret = playlistRepository.save(pl);
       return ret;
    }

    public Playlist findPlaylistById(int id) {
        return playlistRepository.findById(id).get();
    }

    public Set<Song> findSongsWithArtistId(int id) {
        Set<Song> list1 = songRepository.findSongByAlbumArtistId(id);
        Set<Song> list2 = songRepository.findSongByArtistId(id);

        list1.addAll(list2);
        return list1;
    }

    public List<SongDto> findSongByNameOrArtistName(String name) {
        List<Song> songs = songRepository.findSongsByName(name);

        List<SongDto> result = new ArrayList<>();
        songs.forEach(s->{
            SongDto sd = new SongDto();
            sd.id=s.getId();
            sd.name=s.getName();
            sd.length=s.getLength();
            sd.artistName=s.getArtist().getName();
            result.add(sd);
        });
        List<Album> albums = albumRepository.findAllByArtist_Name(name);
        albums.forEach(album -> {
            album.getSongs().forEach(s -> {
                SongDto sd = new SongDto();
                sd.id=s.getId();
                sd.name=s.getName();
                sd.length=s.getLength();
                sd.artistName=s.getArtist().getName();
                result.add(sd);
            });
        });

        return result;
    }

    public Optional<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }


    public List<Playlist> getAllPlaylists() {
        List<Playlist> playList = playlistRepository.findAll();
        return playList;
    }
}
