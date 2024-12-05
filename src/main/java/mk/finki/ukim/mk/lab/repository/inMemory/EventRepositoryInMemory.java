package mk.finki.ukim.mk.lab.repository.inMemory;


import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryInMemory {


    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> searchEvents(String text) {
        return DataHolder.events.stream()
                .filter(event -> event.getName().toLowerCase().contains(text.toLowerCase()) ||
                        event.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Event> save(String name, String description, double popularityScore, Location location) {
        Event newEvent = new Event(name, description, popularityScore, location);
        DataHolder.events.removeIf(e -> Objects.equals(e.getName(), name));
        DataHolder.events.add(newEvent);
        return Optional.of(newEvent);
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        DataHolder.events.removeIf(event -> event.getId().equals(id));
    }


}