package ec.com.reactive.music.persistence.repository;

import ec.com.reactive.music.persistence.entities.Playlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IPlaylistRepository extends ReactiveMongoRepository<Playlist, String> {
}
