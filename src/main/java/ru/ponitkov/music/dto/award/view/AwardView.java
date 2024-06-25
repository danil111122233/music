package ru.ponitkov.music.dto.award.view;

import lombok.Builder;

@Builder
public record AwardView(String id,
                        String musicArtistId,
                        String year,
                        String place) {
}
