package ca.app.integration.service.helper;

public interface EventFactory {
    public Event getSubscribeEvent(String eventUrl, String token);
}
