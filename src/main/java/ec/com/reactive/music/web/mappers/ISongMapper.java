package ec.com.reactive.music.web.mappers;

import ec.com.reactive.music.domain.dto.song.SongDetailDTO;
import ec.com.reactive.music.domain.dto.song.SongSaveDTO;
import ec.com.reactive.music.persistence.entities.Song;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISongMapper {
    Song saveDTOToEntity(SongSaveDTO dto);

    SongDetailDTO entityToDetailDTO(Song song);
    List<SongDetailDTO> entitiesToDetailDTOs(List<Song> songs);
}
