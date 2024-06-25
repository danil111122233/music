package ru.ponitkov.music.manager;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.ponitkov.music.connection.ConnectionGetter;

@UtilityClass
public class ConnectionManager {
    @Getter
    private static final ConnectionGetter connectionGetter;

    static {
        connectionGetter = new ConnectionGetter();
    }
}
