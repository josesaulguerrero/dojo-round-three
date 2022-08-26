package ec.com.reactive.music.web.mappers;

import ec.com.reactive.music.domain.dto.album.AlbumDetailDTO;
import ec.com.reactive.music.domain.dto.album.AlbumSaveDTO;
import ec.com.reactive.music.persistence.entities.Album;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAlbumMapper {
    Album saveDTOToEntity(AlbumSaveDTO dto);

    AlbumDetailDTO entityToDetailDTO(Album entity);
    List<AlbumDetailDTO> entitiesToDetailDTOs(List<Album> entities);
}
