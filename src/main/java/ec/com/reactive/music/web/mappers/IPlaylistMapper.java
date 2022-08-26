package ec.com.reactive.music.web.mappers;

import 
        ec.com.reactive.music.domain.dto.playlist.PlaylistDetailDTO;
import ec.com.reactive.music.domain.dto.playlist.PlaylistSaveDTO;
import ec.com.reactive.music.persistence.entities.Playlist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPlaylistMapper {
    Playlist saveDTOToEntity(PlaylistSaveDTO dto);

    PlaylistDetailDTO entityToDetailDTO(Playlist playlist);
    List<PlaylistDetailDTO> entitiesToDetailDTOs(List<Playlist> playlists);
}
