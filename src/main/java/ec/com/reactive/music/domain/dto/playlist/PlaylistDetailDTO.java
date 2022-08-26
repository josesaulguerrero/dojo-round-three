package ec.com.reactive.music.domain.dto.playlist;

import ec.com.reactive.music.domain.dto.song.SongDetailDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class PlaylistDetailDTO {
    private String playlistId;
    private String name;
    private String username;
    private List<SongDetailDTO> songs;
    private LocalTime duration;
}

