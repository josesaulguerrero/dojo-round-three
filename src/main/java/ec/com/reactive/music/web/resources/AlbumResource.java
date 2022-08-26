package ec.com.reactive.music.web.controllers;

import ec.com.reactive.music.domain.dto.album.AlbumDetailDTO;
import ec.com.reactive.music.domain.dto.album.AlbumSaveDTO;
import ec.com.reactive.music.domain.service.IAlbumService;
import ec.com.reactive.music.persistence.entities.Album;
import ec.com.reactive.music.web.mappers.IAlbumMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("albums")
@AllArgsConstructor
public class AlbumResource {
    private final IAlbumService albumService;
    @Autowired
    private final IAlbumMapper albumMapper;

    @GetMapping("all")
    public ResponseEntity<Flux<AlbumDetailDTO>> getAll() {
        Flux<AlbumDetailDTO> dtos = this.albumService.findAllAlbums()
                .flatMap(album -> Flux.just(this.albumMapper.entityToDetailDTO(album)));
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Mono<AlbumDetailDTO>> getById(@PathVariable("id") String id) {
        Mono<AlbumDetailDTO> dto = this.albumService.findAlbumById(id)
                .flatMap(album -> Mono.just(this.albumMapper.entityToDetailDTO(album)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<Mono<AlbumDetailDTO>> post(@RequestBody AlbumSaveDTO dto) {
        Album album = this.albumMapper.saveDTOToEntity(dto);
        Mono<AlbumDetailDTO> mappedDTO = this.albumService.saveAlbum(album)
                .map(this.albumMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Mono<AlbumDetailDTO>> put(@PathVariable("id") String id, @RequestBody AlbumSaveDTO dto) {
        Album album = this.albumMapper.saveDTOToEntity(dto);
        Mono<AlbumDetailDTO> mappedDTO = this.albumService.updateAlbum(id, album)
                .map(this.albumMapper::entityToDetailDTO);
        return new ResponseEntity<>(mappedDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Mono<AlbumDetailDTO>> delete(@PathVariable("id") String id) {
        Mono<AlbumDetailDTO> dto = this.albumService.deleteAlbum(id)
                .flatMap(album -> Mono.just(this.albumMapper.entityToDetailDTO(album)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
