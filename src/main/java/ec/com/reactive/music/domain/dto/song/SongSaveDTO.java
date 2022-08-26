package ec.com.reactive.music.domain.dto.song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class SongSaveDTO {
    private String idSong;
    private String albumId;
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private LocalTime duration;
}
