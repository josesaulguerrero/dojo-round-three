package ec.com.reactive.music.domain.service;

import ec.com.reactive.music.persistence.entities.Playlist;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
public interface IPlaylistService {
    Flux<Playlist> findAllPlaylists();

    Mono<Playlist> findPlaylistById(String id);

    Mono<Playlist> savePlaylist(Playlist playlist);

    Mono<Playlist> updatePlaylist(String id, Playlist playlist);

    Mono<Playlist> addSong(String playlistId, String songId);

    Mono<Playlist> removeSong(String playlistId, String songId);

    Mono<Playlist> deletePlaylist(String id);
}
