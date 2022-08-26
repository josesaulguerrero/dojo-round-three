package ec.com.reactive.music.domain.service.implementation;

import ec.com.reactive.music.domain.service.IAlbumService;
import ec.com.reactive.music.infrastructure.exceptions.types.HttpException;
import ec.com.reactive.music.persistence.entities.Album;
import ec.com.reactive.music.persistence.repository.IAlbumRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements IAlbumService {
    private final IAlbumRepository albumRepository;


    @Override
    public Flux<Album> findAllAlbums() {
        return this.albumRepository.findAll()
                .switchIfEmpty(
                        Mono.error(new HttpException("The are no albums yet.", HttpStatus.NO_CONTENT))
                );
    }

    @Override
    public Mono<Album> findAlbumById(String id) {
        return this.albumRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new HttpException("The given id does not belong to any of the saved albums.", HttpStatus.NOT_FOUND))
                );
    }

    @Override
    public Mono<Album> saveAlbum(Album album) {
        return this.albumRepository.save(album)
                .switchIfEmpty(
                        Mono.error(new HttpException("The album could not be saved.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    @Override
    public Mono<Album> updateAlbum(String id, Album album) {
        return this.findAlbumById(id)
                .flatMap(entityFromDB -> this.albumRepository.save(album))
                .switchIfEmpty(
                        Mono.error(new HttpException("The album could not be updated.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    @Override
    public Mono<Album> deleteAlbum(String id) {
        return this.findAlbumById(id)
                .flatMap(album -> this.albumRepository.deleteById(id).thenReturn(album));
    }
}
