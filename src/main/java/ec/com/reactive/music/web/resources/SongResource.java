package ec.com.reactive.music.web.resources;


import ec.com.reactive.music.domain.dto.song.SongDetailDTO;
import ec.com.reactive.music.domain.dto.song.SongSaveDTO;
import ec.com.reactive.music.domain.service.ISongService;
import ec.com.reactive.music.persistence.entities.Song;
import ec.com.reactive.music.web.mappers.ISongMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("songs")
@AllArgsConstructor
public class SongResource {
    private ISongService songService;
    private ISongMapper songMapper;

    @GetMapping("all")
    public ResponseEntity<Flux<SongDetailDTO>> getAll() {
        Flux<SongDetailDTO> dtos = this.songService.findAllSongs()
                .flatMap(song -> Flux.just(this.songMapper.entityToDetailDTO(song)));
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Mono<SongDetailDTO>> getById(@PathVariable("id") String id) {
        Mono<SongDetailDTO> dto = this.songService.findSongById(id)
                .flatMap(song -> Mono.just(this.songMapper.entityToDetailDTO(song)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<Mono<SongDetailDTO>> post(@RequestBody SongSaveDTO dto) {
        Song song = this.songMapper.saveDTOToEntity(dto);
        Mono<SongDetailDTO> mappedDTO = this.songService.saveSong(song)
                .map(this.songMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Mono<SongDetailDTO>> put(@PathVariable("id") String id, @RequestBody SongSaveDTO dto) {
        Song song = this.songMapper.saveDTOToEntity(dto);
        Mono<SongDetailDTO> mappedDTO = this.songService.updateSong(id, song)
                .map(this.songMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Mono<SongDetailDTO>> delete(@PathVariable("id") String id) {
        Mono<SongDetailDTO> dto = this.songService.deleteSong(id)
                .flatMap(song -> Mono.just(this.songMapper.entityToDetailDTO(song)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
