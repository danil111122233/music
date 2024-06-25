package ru.ponitkov.music.mapper;

import ru.ponitkov.music.dto.nominationartist.controller.NominationArtistController;
import ru.ponitkov.music.dto.nominationartist.view.NominationArtistView;
import ru.ponitkov.music.entity.NominationArtist;

public class NominationArtistMapper {
    public NominationArtist mapToEntity(NominationArtistController obj) {
        return NominationArtist.builder()
                .id(obj.id())
                .musicArtistId(obj.musicArtistId())
                .awardId(obj.awardId())
                .name(obj.name())
                .build();
    }

    public NominationArtistController mapToController(NominationArtist obj) {
        return NominationArtistController.builder()
                .id(obj.getId())
                .musicArtistId(obj.getMusicArtistId())
                .awardId(obj.getAwardId())
                .name(obj.getName())
                .build();
    }

    public NominationArtistView mapToView(NominationArtistController obj) {
        return NominationArtistView.builder()
                .id(String.valueOf(obj.id()))
                .musicArtistId(String.valueOf(obj.musicArtistId()))
                .awardId(String.valueOf(obj.awardId()))
                .name(obj.name())
                .build();
    }
}
