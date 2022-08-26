package ec.com.reactive.music.domain.dto.album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AlbumSaveDTO {
    private String name;
    private String artist;
    private Long releaseYear;
}