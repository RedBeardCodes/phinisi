package io.github.redbeardcodes;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class Main extends Application {
    // Needed to enable Jakarta REST and specify path.
}
