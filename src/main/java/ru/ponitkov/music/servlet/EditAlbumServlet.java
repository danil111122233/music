package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.dto.album.view.AlbumView;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.manager.MapperManager;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.service.AlbumService;
import ru.ponitkov.music.util.JspPathCreator;

import java.io.IOException;
import java.time.LocalDate;

import static ru.ponitkov.music.util.AttributeGetter.MUSIC_ARTIST;
import static ru.ponitkov.music.util.UrlPathGetter.ACCOUNT_URL;
import static ru.ponitkov.music.util.UrlPathGetter.getFullPath;

@WebServlet("/album/edit")
public class EditAlbumServlet extends HttpServlet {
    private AlbumService albumService;
    private AlbumMapper albumMapper;

    @Override
    public void init() throws ServletException {
        albumService = ServiceManager.getAlbumService();
        albumMapper = MapperManager.getAlbumMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("idAlbum"));
        AlbumView albumView = albumMapper.mapToView(albumService.findById(id));

        req.setAttribute("album", albumView);
        req.getRequestDispatcher(JspPathCreator.getDefaultJspPath("editAlbum")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final MusicArtistController controller = (MusicArtistController) req.getSession().getAttribute(MUSIC_ARTIST);
        final Long id = Long.parseLong(req.getParameter("idAlbum"));

        final AlbumController albumController = albumService.findById(id);

        String title = req.getParameter("title");
        String release = req.getParameter("release");
        AlbumController newAlbum = AlbumController.builder()
                .id(albumController.id())
                .musicArtistId(controller.id())
                .title(title)
                .release(LocalDate.parse(release))
                .build();

        albumService.update(newAlbum);
        resp.sendRedirect(getFullPath(ACCOUNT_URL));
    }
}
