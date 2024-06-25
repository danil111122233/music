package ru.ponitkov.music.mapper;

import ru.ponitkov.music.dto.award.controller.AwardController;
import ru.ponitkov.music.dto.award.view.AwardView;
import ru.ponitkov.music.entity.Award;

public class AwardMapper {
    public Award mapToEntity(AwardController obj) {
        return Award.builder()
                .id(obj.id())
                .musicArtistId(obj.musicArtistId())
                .year(obj.year())
                .place(obj.place())
                .build();
    }

    public AwardController mapToController(Award obj) {
        return AwardController.builder()
                .id(obj.getId())
                .musicArtistId(obj.getMusicArtistId())
                .year(obj.getYear())
                .place(obj.getPlace())
                .build();
    }

    public AwardView mapToView(AwardController obj) {
        return AwardView.builder()
                .id(String.valueOf(obj.id()))
                .musicArtistId(String.valueOf(obj.musicArtistId()))
                .year(String.valueOf(obj.year()))
                .place(String.valueOf(obj.place()))
                .build();
    }
}
