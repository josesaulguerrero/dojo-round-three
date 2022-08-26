package ec.com.reactive.music.domain.service;

import ec.com.reactive.music.persistence.entities.Album;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlbumService {
    Flux<Album> findAllAlbums();

    Mono<Album> findAlbumById(String id);

    Mono<Album> saveAlbum(Album album);

    Mono<Album> updateAlbum(String id, Album album);

    Mono<Album> deleteAlbum(String id);
}
