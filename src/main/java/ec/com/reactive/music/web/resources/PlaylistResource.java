package ec.com.reactive.music.web.resources;


import ec.com.reactive.music.domain.dto.playlist.PlaylistDetailDTO;
import ec.com.reactive.music.domain.dto.playlist.PlaylistSaveDTO;
import ec.com.reactive.music.domain.service.IPlaylistService;
import ec.com.reactive.music.persistence.entities.Playlist;
import ec.com.reactive.music.web.mappers.IPlaylistMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("playlists")
@AllArgsConstructor
public class PlaylistResource {
    private IPlaylistService playlistService;
    private IPlaylistMapper playlistMapper;

    @GetMapping("all")
    public ResponseEntity<Flux<PlaylistDetailDTO>> getAll() {
        Flux<PlaylistDetailDTO> dtos = this.playlistService.findAllPlaylists()
                .flatMap(playlist -> Flux.just(this.playlistMapper.entityToDetailDTO(playlist)));
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Mono<PlaylistDetailDTO>> getById(@PathVariable("id") String id) {
        Mono<PlaylistDetailDTO> dto = this.playlistService.findPlaylistById(id)
                .flatMap(playlist -> Mono.just(this.playlistMapper.entityToDetailDTO(playlist)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<Mono<PlaylistDetailDTO>> post(@RequestBody PlaylistSaveDTO dto) {
        Playlist playlist = this.playlistMapper.saveDTOToEntity(dto);
        Mono<PlaylistDetailDTO> mappedDTO = this.playlistService.savePlaylist(playlist)
                .map(this.playlistMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Mono<PlaylistDetailDTO>> put(@PathVariable("id") String id, @RequestBody PlaylistSaveDTO dto) {
        Playlist playlist = this.playlistMapper.saveDTOToEntity(dto);
        Mono<PlaylistDetailDTO> mappedDTO = this.playlistService.updatePlaylist(id, playlist)
                .map(this.playlistMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.OK);
    }

    @PatchMapping("{playlistId}/songs/add/{songId}")
    public ResponseEntity<Mono<PlaylistDetailDTO>> addSong(@PathVariable("playlistId") String playlistId, @PathVariable("songId") String songId) {
        Mono<PlaylistDetailDTO> mappedDTO = this.playlistService.addSong(playlistId, songId)
                .map(this.playlistMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.OK);
    }

    @PatchMapping("{playlistId}/songs/remove/{songId}")
    public ResponseEntity<Mono<PlaylistDetailDTO>> removeSong(@PathVariable("playlistId") String playlistId, @PathVariable("songId") String songId) {
        Mono<PlaylistDetailDTO> mappedDTO = this.playlistService.removeSong(playlistId, songId)
                .map(this.playlistMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Mono<PlaylistDetailDTO>> delete(@PathVariable("id") String id) {
        Mono<PlaylistDetailDTO> dto = this.playlistService.deletePlaylist(id)
                .flatMap(playlist -> Mono.just(this.playlistMapper.entityToDetailDTO(playlist)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
