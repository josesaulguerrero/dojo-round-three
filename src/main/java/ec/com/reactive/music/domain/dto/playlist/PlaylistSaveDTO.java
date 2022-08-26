package ec.com.reactive.music.domain.dto.playlist;

import ec.com.reactive.music.domain.dto.song.SongDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSaveDTO {
    private String name;
    private String username;
}

