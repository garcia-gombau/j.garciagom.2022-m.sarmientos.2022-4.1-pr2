package es.urjc.code.daw.library.notification;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent {

    private final String message;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
