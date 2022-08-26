package ec.com.reactive.music.domain.service.implementation;

import ec.com.reactive.music.domain.service.ISongService;
import ec.com.reactive.music.infrastructure.exceptions.types.HttpException;
import ec.com.reactive.music.persistence.entities.Playlist;
import ec.com.reactive.music.persistence.entities.Song;
import ec.com.reactive.music.persistence.repository.IPlaylistRepository;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PlaylistServiceImplTest {
    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @Mock
    private IPlaylistRepository playlistRepository;

    @Mock
    private ISongService songService;

    @Test
    @DisplayName("#findAllPlaylists should throw an error when it returns an empty flux.")
    void findAllPlaylists_ShouldReturnAnError_WhenNoRecordsExist() {
        // arrange
        BDDMockito
                .when(playlistRepository.findAll())
                .thenReturn(Flux.empty());

        // act
        Flux<Playlist> data = this.playlistService.findAllPlaylists();

        // assert
        StepVerifier.create(data)
                .expectSubscription()
                .expectError(HttpException.class)
                .verify();
    }

    @Test
    @DisplayName("#addSong should add a song to the playlist")
    void addSong_ShouldAddASongToThePlaylist_WhenSuccessful() {
        // arrange
        Playlist playlist = new Playlist(UUID.randomUUID().toString(), "custom playlist", "pepito", new ArrayList<>(), LocalTime.of(0, 0, 0));
        Song song = new Song(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "custom song", "composer", "producer", "arranger", LocalTime.of(0, 3, 45));
        BDDMockito
                .when(playlistRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(playlist));
        BDDMockito
                .when(playlistRepository.save(ArgumentMatchers.any(Playlist.class)))
                .thenReturn(Mono.just(playlist));
        BDDMockito
                .when(songService.findSongById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(song));

        // act
        Mono<Playlist> data = this.playlistService.addSong(playlist.getPlaylistId(), song.getSongId());

        // assert
        StepVerifier.create(data)
                .expectSubscription()
                .expectNextMatches(p -> p.getSongs().size() == 1 && p.getDuration().equals(LocalTime.of(0,3,45)))
                .verifyComplete();
    }

}