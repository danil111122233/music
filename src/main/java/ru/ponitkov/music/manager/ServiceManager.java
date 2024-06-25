package ru.ponitkov.music.manager;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.ponitkov.music.service.AlbumService;
import ru.ponitkov.music.service.AwardService;
import ru.ponitkov.music.service.MusicArtistService;
import ru.ponitkov.music.service.NominationArtistService;

@UtilityClass
public class ServiceManager {
    @Getter
    private static final MusicArtistService musicArtistService;
    @Getter
    private static final AlbumService albumService;
    @Getter
    private static final AwardService awardService;
    @Getter
    private static final NominationArtistService nominationArtistService;

    static {
        musicArtistService = new MusicArtistService(RepositoryManager.getMusicArtistRepository(), MapperManager.getMusicArtistMapper());
        albumService = new AlbumService(RepositoryManager.getAlbumRepository(), MapperManager.getAlbumMapper());
        awardService = new AwardService(RepositoryManager.getAwardRepository(), MapperManager.getAwardMapper());
        nominationArtistService = new NominationArtistService(RepositoryManager.getNominationArtistRepository(), MapperManager.getNominationArtistMapper());
    }
}
