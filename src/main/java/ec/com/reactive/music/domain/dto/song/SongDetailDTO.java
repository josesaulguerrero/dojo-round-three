package ec.com.reactive.music.domain.dto.song;

import lombok.*;

import java.time.LocalTime;


@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class SongDetailDTO {
    private String idSong;
    private String albumId;
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private LocalTime duration;
}
