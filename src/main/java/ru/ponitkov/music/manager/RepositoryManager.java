package ru.ponitkov.music.manager;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.ponitkov.music.repository.AlbumRepository;
import ru.ponitkov.music.repository.AwardRepository;
import ru.ponitkov.music.repository.MusicArtistRepository;
import ru.ponitkov.music.repository.NominationArtistRepository;

@UtilityClass
public class RepositoryManager {
    @Getter
    private static final MusicArtistRepository musicArtistRepository;

    @Getter
    private static final AlbumRepository albumRepository;

    @Getter
    private static final AwardRepository awardRepository;

    @Getter
    private static final NominationArtistRepository nominationArtistRepository;

    static {
        musicArtistRepository = new MusicArtistRepository(ConnectionManager.getConnectionGetter());
        albumRepository = new AlbumRepository(ConnectionManager.getConnectionGetter());
        awardRepository = new AwardRepository(ConnectionManager.getConnectionGetter());
        nominationArtistRepository = new NominationArtistRepository(ConnectionManager.getConnectionGetter());
    }
}
