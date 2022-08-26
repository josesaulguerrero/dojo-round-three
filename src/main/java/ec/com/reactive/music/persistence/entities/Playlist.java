package ec.com.reactive.music.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private List<Song> songs;
    private LocalTime duration;

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void removeSong(Song song) {
        this.songs = this.songs.stream()
                .filter(s -> !Objects.equals(s.getSongId(), song.getSongId()))
                .collect(Collectors.toList());
    }
}
