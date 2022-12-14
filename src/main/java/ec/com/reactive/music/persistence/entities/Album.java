package ec.com.reactive.music.persistence.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "albums")
public class Album {
    @Id
    private String albumId;
    private String name;
    private String artist;
    private Long releaseYear;
}
