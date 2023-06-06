package es.urjc.code.daw.library.notification;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PreUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationService implements ApplicationEventPublisherAware {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);
    ApplicationEventPublisher eventPublisher;

    public void notify(String message) {
        logger.info(message);
    }

    public void asynchronousNotify(String message){
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @EventListener
    public void logger(MessageEvent message){
        logger.info("Async -> " + message.getMessage());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}

