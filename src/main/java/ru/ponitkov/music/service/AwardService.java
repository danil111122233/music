package ru.ponitkov.music.service;

import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.dto.award.controller.AwardController;
import ru.ponitkov.music.mapper.AwardMapper;
import ru.ponitkov.music.repository.AwardRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AwardService {
    private static final Logger LOGGER = Logger.getLogger(AwardService.class.getName());
    private final AwardRepository awardRepository;
    private final AwardMapper awardMapper;

    public AwardService(AwardRepository awardRepository, AwardMapper awardMapper) {
        this.awardRepository = awardRepository;
        this.awardMapper = awardMapper;
    }

    public void insert(AwardController controller) {
        awardRepository.insert(awardMapper.mapToEntity(controller));
    }

    public void update(AwardController controller) {
        awardRepository.update(awardMapper.mapToEntity(controller));
    }

    public void delete(Long id) {
        awardRepository.delete(id);
    }

    public List<AwardController> findByMusicArtistId(Long id) {
        return awardRepository.findByMusicArtistId(id).stream().map(awardMapper::mapToController).collect(Collectors.toList());
    }
}
