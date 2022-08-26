package ec.com.reactive.music.domain.dto.album;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class AlbumDetailDTO {
    private String albumId;
    private String name;
    private String artist;
    private Long releaseYear;
}