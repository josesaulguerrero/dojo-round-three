package ec.com.reactive.music.domain.service.implementation;

import ec.com.reactive.music.domain.service.ISongService;
import ec.com.reactive.music.infrastructure.exceptions.types.HttpException;
import ec.com.reactive.music.persistence.entities.Song;
import ec.com.reactive.music.persistence.repository.ISongRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class SongServiceImpl implements ISongService {
    private final ISongRepository songRepository;

    @Override
    public Flux<Song> findAllSongs() {
        return this.songRepository.findAll()
                .switchIfEmpty(
                        Mono.error(new HttpException("The are no songs yet.", HttpStatus.NO_CONTENT))
                );
    }

    @Override
    public Mono<Song> findSongById(String id) {
        return this.songRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new HttpException("The given id does not belong to any of the saved songs.", HttpStatus.NOT_FOUND))
                );
    }

    @Override
    public Mono<Song> saveSong(Song song) {
        return this.songRepository.save(song)
                .switchIfEmpty(
                        Mono.error(new HttpException("The song could not be saved.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    @Override
    public Mono<Song> updateSong(String songId, Song song) {
        song.setSongId(songId);
        return this.findSongById(songId)
                .flatMap(entityFromDB -> this.songRepository.save(song))
                .switchIfEmpty(
                        Mono.error(new HttpException("The song could not be updated.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    @Override
    public Mono<Song> deleteSong(String songId) {
        return this.findSongById(songId)
                .flatMap(song -> this.songRepository.deleteById(songId).thenReturn(song));
    }
}
