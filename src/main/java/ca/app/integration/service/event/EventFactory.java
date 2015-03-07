package ca.app.integration.service.event;

public interface EventFactory {
    public Event getSubscribeEvent(String eventUrl, String token);
}
