package ec.com.reactive.music.domain.dto.playlist;

import com.fasterxml.jackson.annotation.JsonFormat;
import ec.com.reactive.music.domain.dto.song.SongDetailDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDetailDTO {
    private String playlistId;
    private String name;
    private String username;
    private ArrayList<SongDetailDTO> songs;
    private LocalTime duration;
}

