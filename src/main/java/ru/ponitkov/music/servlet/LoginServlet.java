package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistLoginController;
import ru.ponitkov.music.dto.musicartist.view.MusicArtistLoginView;
import ru.ponitkov.music.manager.MapperManager;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.mapper.MusicArtistMapper;
import ru.ponitkov.music.service.MusicArtistService;
import ru.ponitkov.music.util.JspPathCreator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static ru.ponitkov.music.util.AttributeGetter.MUSIC_ARTIST;
import static ru.ponitkov.music.util.JspPathGetter.LOGIN_JSP;
import static ru.ponitkov.music.util.UrlPathGetter.LOGIN_URL;
import static ru.ponitkov.music.util.UrlPathGetter.ACCOUNT_URL;
import static ru.ponitkov.music.util.UrlPathGetter.getFullPath;

@WebServlet(LOGIN_URL)
public class LoginServlet extends HttpServlet {
    private static MusicArtistService musicArtistService;
    private static MusicArtistMapper musicArtistMapper;

    @Override
    public void init() {
        musicArtistService = ServiceManager.getMusicArtistService();
        musicArtistMapper = MapperManager.getMusicArtistMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathCreator.getLoginJspPath(LOGIN_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final MusicArtistLoginView view = new MusicArtistLoginView(req.getParameter("email"), req.getParameter("password"));
        final MusicArtistLoginController controller = musicArtistMapper.mapToLoginController(view);

        try {
            Optional<MusicArtistController> controllerDto = musicArtistService.login(controller);

            if (controllerDto.isPresent()) {
                req.getSession().setAttribute(MUSIC_ARTIST, controllerDto.get());
                resp.sendRedirect(getFullPath(ACCOUNT_URL));
            } else {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher(JspPathCreator.getLoginJspPath(LOGIN_JSP)).forward(req, resp);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
