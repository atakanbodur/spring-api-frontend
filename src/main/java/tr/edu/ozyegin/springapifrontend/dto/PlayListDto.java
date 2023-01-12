package tr.edu.ozyegin.springapifrontend.dto;

import tr.edu.ozyegin.springapifrontend.model.Song;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PlayListDto {
    public String name;
    public List<SongDto> songs;
}
