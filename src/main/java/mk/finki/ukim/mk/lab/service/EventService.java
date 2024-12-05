package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    void deleteById(Long id);
    Optional<Event> save(String name, String description, double popularityScore, Long locationId);
    Optional<Event> findById(Long id);
    List<Event> findByLocationId(Long location_id);
}