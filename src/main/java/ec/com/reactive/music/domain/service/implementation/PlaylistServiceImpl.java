package ec.com.reactive.music.domain.service.implementation;

import ec.com.reactive.music.domain.service.IPlaylistService;
import ec.com.reactive.music.domain.service.ISongService;
import ec.com.reactive.music.infrastructure.exceptions.types.HttpException;
import ec.com.reactive.music.persistence.entities.Playlist;
import ec.com.reactive.music.persistence.entities.Song;
import ec.com.reactive.music.persistence.repository.IPlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements IPlaylistService {
    private final IPlaylistRepository playlistRepository;
    private final ISongService songService;

    @Override
    public Flux<Playlist> findAllPlaylists() {
        return this.playlistRepository.findAll()
                .switchIfEmpty(
                        Mono.error(new HttpException("The are no playlists yet.", HttpStatus.NO_CONTENT))
                );
    }

    @Override
    public Mono<Playlist> findPlaylistById(String id) {
        return this.playlistRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new HttpException("The given id does not belong to any of the saved playlists.", HttpStatus.NOT_FOUND))
                );
    }

    @Override
    public Mono<Playlist> savePlaylist(Playlist playlist) {
        playlist.setDuration(LocalTime.of(0,0,0));
        playlist.setSongs(new ArrayList<>());
        return this.playlistRepository.save(playlist)
                .switchIfEmpty(
                        Mono.error(new HttpException("The playlist could not be saved.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    @Override
    public Mono<Playlist> updatePlaylist(String playlistId, Playlist playlist) {
        playlist.setPlaylistId(playlistId);
        return this.findPlaylistById(playlistId)
                .flatMap(entityFromDB -> {
                    entityFromDB.setName(playlist.getName());
                    return this.playlistRepository.save(entityFromDB);
                })
                .switchIfEmpty(
                        Mono.error(new HttpException("The album could not be updated.", HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }

    private LocalTime calculatePlaylistDuration(Playlist playlist) {
        return playlist.getSongs().stream()
                .map(Song::getDuration)
                .reduce(
                        LocalTime.of(0, 0, 0),
                        (LocalTime accumulator, LocalTime songDuration) -> accumulator
                                .plusHours(songDuration.getHour())
                                .plusMinutes(songDuration.getMinute())
                                .plusSeconds(songDuration.getSecond())
                );
    }

    @Override
    public Mono<Playlist> addSong(String playlistId, String songId) {
        return this.findPlaylistById(playlistId)
                .flatMap(playlist -> this.songService.findSongById(songId)
                        .doOnNext(playlist::addSong).thenReturn(playlist)
                )
                .map(playlist -> {
                    playlist.setDuration(this.calculatePlaylistDuration(playlist));
                    return playlist;
                })
                .flatMap(this.playlistRepository::save);
    }

    @Override
    public Mono<Playlist> removeSong(String playlistId, String songId) {
        return this.findPlaylistById(playlistId)
                .flatMap(playlist -> this.songService.findSongById(songId)
                        .doOnNext(playlist::removeSong).thenReturn(playlist)
                )
                .map(playlist -> {
                    playlist.setDuration(this.calculatePlaylistDuration(playlist));
                    return playlist;
                })
                .flatMap(this.playlistRepository::save);
    }

    @Override
    public Mono<Playlist> deletePlaylist(String playlistId) {
        return this.findPlaylistById(playlistId)
                .flatMap(album -> this.playlistRepository.deleteById(playlistId).thenReturn(album));
    }
}
