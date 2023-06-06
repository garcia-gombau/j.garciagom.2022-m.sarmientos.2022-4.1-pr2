package es.urjc.code.daw.library.features;

import jdk.jfr.Label;
import org.togglz.core.Feature;

public enum AppFeatures implements Feature {
    @Label("Line breaker Feature")
    LINEBREAKER,
    @Label("Async Events Feature")
    ASYNC_EVENTS
}
