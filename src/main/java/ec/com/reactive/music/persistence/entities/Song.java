package ec.com.reactive.music.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "songs")
public class Song {
    @Id
    private String songId;
    private String albumId;
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private LocalTime duration;
}