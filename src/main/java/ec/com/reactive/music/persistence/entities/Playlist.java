package ec.com.reactive.music.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.ArrayList;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String playlistId;
    private String name;
    private String username;
    private ArrayList<Song> songs;
    private LocalTime duration;
}
