package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistRegistrationController;
import ru.ponitkov.music.dto.musicartist.view.MusicArtistRegistrationView;
import ru.ponitkov.music.manager.MapperManager;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.manager.ValidatorManager;
import ru.ponitkov.music.mapper.MusicArtistMapper;
import ru.ponitkov.music.service.MusicArtistService;
import ru.ponitkov.music.util.JspPathCreator;
import ru.ponitkov.music.util.UrlPathGetter;
import ru.ponitkov.music.validator.RegistrationValidator;

import java.io.IOException;

import static ru.ponitkov.music.util.AttributeGetter.ERROR;
import static ru.ponitkov.music.util.JspPathGetter.REGISTRATION_JSP;
import static ru.ponitkov.music.util.UrlPathGetter.LOGIN_URL;
import static ru.ponitkov.music.util.UrlPathGetter.REGISTRATION_URL;

@WebServlet(REGISTRATION_URL)
public class RegistrationServlet extends HttpServlet {
    private static MusicArtistService musicArtistService;
    private static MusicArtistMapper musicArtistMapper;
    private static RegistrationValidator validator;

    private static MusicArtistRegistrationView getMusicArtistRegistrationView(HttpServletRequest req) {
        return MusicArtistRegistrationView.builder()
                .name(req.getParameter("name"))
                .debutYear(req.getParameter("debutYear"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
    }

    @Override
    public void init(ServletConfig config) {
        musicArtistService = ServiceManager.getMusicArtistService();
        musicArtistMapper = MapperManager.getMusicArtistMapper();
        validator = ValidatorManager.getRegistrationValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathCreator.getLoginJspPath(REGISTRATION_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final MusicArtistRegistrationView musicArtistRegistrationView = getMusicArtistRegistrationView(req);

        if (validator.validate(musicArtistRegistrationView)) {
            MusicArtistRegistrationController controller = musicArtistMapper.mapToRegistrationController(musicArtistRegistrationView);
            musicArtistService.insert(controller);

            resp.sendRedirect(UrlPathGetter.getFullPath(LOGIN_URL));
        } else {
            req.setAttribute(ERROR, "Ошибка регистрации.");
            doGet(req, resp);
        }
    }
}
