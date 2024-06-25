package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.dto.album.view.AlbumView;
import ru.ponitkov.music.dto.award.view.AwardView;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.manager.MapperManager;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.mapper.AwardMapper;
import ru.ponitkov.music.service.AlbumService;
import ru.ponitkov.music.service.AwardService;
import ru.ponitkov.music.util.JspPathCreator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.ponitkov.music.util.AttributeGetter.MUSIC_ARTIST;
import static ru.ponitkov.music.util.JspPathGetter.ACCOUNT_JSP;
import static ru.ponitkov.music.util.UrlPathGetter.ACCOUNT_URL;

@WebServlet(ACCOUNT_URL)
public class AccountServlet extends HttpServlet {
    private AlbumService albumService;
    private AlbumMapper albumMapper;
    private AwardService awardService;
    private AwardMapper awardMapper;

    @Override
    public void init() throws ServletException {
        albumService = ServiceManager.getAlbumService();
        albumMapper = MapperManager.getAlbumMapper();
        awardService = ServiceManager.getAwardService();
        awardMapper = MapperManager.getAwardMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final MusicArtistController controller = (MusicArtistController) req.getSession().getAttribute(MUSIC_ARTIST);
        final List<AlbumView> albumViewList = getAlbums(controller);
        final List<AwardView> awardViewList = getAwards(controller);

        req.setAttribute("albums", albumViewList);
        req.setAttribute("awards", awardViewList);
        req.setAttribute("artistName", controller.name());

        req.getRequestDispatcher(JspPathCreator.getDefaultJspPath(ACCOUNT_JSP)).forward(req, resp);
    }

    private List<AlbumView> getAlbums(MusicArtistController musicArtistController) {
        return albumService.findByMusicArtistId(musicArtistController.id()).stream()
                .map(albumMapper::mapToView)
                .toList();
    }

    private List<AwardView> getAwards(MusicArtistController musicArtistController) {
        return awardService.findByMusicArtistId(musicArtistController.id()).stream()
                .map(awardMapper::mapToView)
                .toList();
    }
}
