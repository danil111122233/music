package ru.ponitkov.music.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.manager.ServiceManager;
import ru.ponitkov.music.service.AlbumService;

import java.io.IOException;

import static ru.ponitkov.music.util.UrlPathGetter.ACCOUNT_URL;
import static ru.ponitkov.music.util.UrlPathGetter.getFullPath;

@WebServlet("/album/delete")
public class DeleteAlbumServlet extends HttpServlet {
    private AlbumService albumService;

    @Override
    public void init() {
        albumService = ServiceManager.getAlbumService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("idAlbum"));

        albumService.delete(id);

        resp.sendRedirect(getFullPath(ACCOUNT_URL));
    }
}
