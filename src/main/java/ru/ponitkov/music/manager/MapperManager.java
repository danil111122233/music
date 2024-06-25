package ru.ponitkov.music.manager;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.mapper.AwardMapper;
import ru.ponitkov.music.mapper.MusicArtistMapper;
import ru.ponitkov.music.mapper.NominationArtistMapper;

@UtilityClass
public class MapperManager {
    @Getter
    private static final MusicArtistMapper musicArtistMapper;

    @Getter
    private static final AlbumMapper albumMapper;

    @Getter
    private static final AwardMapper awardMapper;

    @Getter
    private static final NominationArtistMapper nominationArtistMapper;

    static {
        musicArtistMapper = new MusicArtistMapper();
        albumMapper = new AlbumMapper();
        awardMapper = new AwardMapper();
        nominationArtistMapper = new NominationArtistMapper();
    }
}
