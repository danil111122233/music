package ru.ponitkov.music.dto.nominationartist.view;

import lombok.Builder;

@Builder
public record NominationArtistView(String id,
                                   String musicArtistId,
                                   String awardId,
                                   String name,
                                   String result) {
}
