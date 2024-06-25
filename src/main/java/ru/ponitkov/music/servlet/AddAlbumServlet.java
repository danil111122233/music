package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.manager.MapperManager;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.service.AlbumService;
import ru.ponitkov.music.util.JspPathCreator;

import java.io.IOException;
import java.time.LocalDate;

import static ru.ponitkov.music.util.AttributeGetter.MUSIC_ARTIST;
import static ru.ponitkov.music.util.JspPathGetter.ACCOUNT_JSP;

@WebServlet("/album/add")
public class AddAlbumServlet extends HttpServlet {
    private AlbumService albumService;
    private AlbumMapper albumMapper;

    @Override
    public void init() throws ServletException {
        albumService = ServiceManager.getAlbumService();
        albumMapper = MapperManager.getAlbumMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final MusicArtistController controller = (MusicArtistController) req.getSession().getAttribute(MUSIC_ARTIST);
        final String title = req.getParameter("title");
        final String release = req.getParameter("release");

        if (title.isEmpty() || release.isEmpty()) {
            req.setAttribute("error", "Title and Release are required!");
            req.getRequestDispatcher(JspPathCreator.getDefaultJspPath(ACCOUNT_JSP)).forward(req, resp);
            return;
        }

        AlbumController albumController = AlbumController.builder()
                .title(title)
                .release(LocalDate.parse(release))
                .musicArtistId(controller.id())
                .build();

        albumService.insert(albumController);
        resp.sendRedirect(req.getContextPath() + "/account");
    }
}
