package ru.ponitkov.music.service;

import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.repository.AlbumRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AlbumService {
    private static final Logger LOGGER = Logger.getLogger(AlbumService.class.getName());
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    public void insert(AlbumController controller) {
        albumRepository.insert(albumMapper.mapToEntity(controller));
    }

    public List<AlbumController> findByMusicArtistId(Long id) {
        return albumRepository.findByMusicArtistId(id).stream().map(albumMapper::mapToController).collect(Collectors.toList());
    }

    public AlbumController findById(Long id) {
        return albumMapper.mapToController(albumRepository.findById(id));
    }

    public void update(AlbumController controller) {
        albumRepository.update(albumMapper.mapToEntity(controller));
    }

    public void delete(Long id) {
        albumRepository.delete(id);
    }
}
