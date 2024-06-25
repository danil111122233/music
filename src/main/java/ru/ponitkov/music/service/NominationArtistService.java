package ru.ponitkov.music.service;

import ru.ponitkov.music.dto.award.controller.AwardController;
import ru.ponitkov.music.dto.nominationartist.controller.NominationArtistController;
import ru.ponitkov.music.mapper.NominationArtistMapper;
import ru.ponitkov.music.repository.NominationArtistRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NominationArtistService {
    private static final Logger LOGGER = Logger.getLogger(NominationArtistService.class.getName());
    private final NominationArtistRepository nominationArtistRepository;
    private final NominationArtistMapper nominationArtistMapper;

    public NominationArtistService(NominationArtistRepository nominationArtistRepository, NominationArtistMapper nominationArtistMapper) {
        this.nominationArtistRepository = nominationArtistRepository;
        this.nominationArtistMapper = nominationArtistMapper;
    }

    public void insert(NominationArtistController controller) {
        nominationArtistRepository.insert(nominationArtistMapper.mapToEntity(controller));
    }

    public void update(NominationArtistController controller) {
        nominationArtistRepository.update(nominationArtistMapper.mapToEntity(controller));
    }

    public void delete(Long id) {
        nominationArtistRepository.delete(id);
    }

    public List<NominationArtistController> findByMusicArtistId(Long id) {
        return nominationArtistRepository.findByMusicArtistId(id).stream().map(nominationArtistMapper::mapToController).collect(Collectors.toList());
    }
}
