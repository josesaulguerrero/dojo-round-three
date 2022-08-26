package ec.com.reactive.music.domain.service;

import ec.com.reactive.music.persistence.entities.Song;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
public interface ISongService {
    Flux<Song> findAllSongs();
    Mono<Song> findSongById(String id);
    Mono<Song> saveSong (Song song);
    Mono<Song> updateSong(String idSong, Song song);
    Mono<Song> deleteSong (String id);
}
