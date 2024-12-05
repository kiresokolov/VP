package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.impl.EventServiceImpl;
import mk.finki.ukim.mk.lab.service.impl.LocationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping({"/", "/events"})
public class EventController {
    private final EventServiceImpl eventService;
    private final LocationServiceImpl locationService;

    public EventController(EventServiceImpl eventService, LocationServiceImpl locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest req) {
        List<Event> eventList;
        String searchName = req.getParameter("searchName");
        String minRating =req.getParameter("minRating");

        if (searchName != null && minRating != null && !Objects.equals(minRating, "")) {
            eventList = eventService.searchEvents(searchName).stream()
                    .filter(event -> event.getPopularityScore() >= Double.parseDouble(minRating))
                    .toList();
        } else if (minRating != null && !Objects.equals(minRating, "")) {
            eventList = eventService.listAll().stream()
                    .filter(event -> event.getPopularityScore() >= Double.parseDouble(minRating))
                    .toList();
        } else if (searchName != null) {
            eventList = eventService.searchEvents(searchName);
        } else {
            eventList = eventService.listAll();
        }

        model.addAttribute("events", eventList);
        return "listEvents";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/add-form")
    public String addEventPage(Model model) {
        List<Location> locationList = locationService.listAll();
        model.addAttribute("locations", locationList);
        return "add-event";
    }

    @GetMapping("/edit-form/{id}")
    public String editEventPage(Model model, @PathVariable Long id) {
        if (this.eventService.findById(id).isPresent()) {
            Event event = this.eventService.findById(id).get();
            List<Location> locationList = locationService.listAll();
            model.addAttribute("locations", locationList);
            model.addAttribute("event", event);
            return "add-event";
        }
        return "redirect:/events";
    }

    @PostMapping("/add")
    public String saveEvent(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double popularityScore,
            @RequestParam Long location
    ) {
        this.eventService.save(name, description, popularityScore, location);
        return "redirect:/events";
    }
    @PostMapping("/event-booking")
    public String handleBooking(
            @RequestParam String rad,
            @RequestParam String numTickets,
            HttpServletRequest req,
            Model model
    ) {
        // Retrieve and set variables for confirmation
        model.addAttribute("hostName", req.getRemoteHost());
        model.addAttribute("hostAddress", req.getRemoteAddr());
        model.addAttribute("eventName", rad);
        model.addAttribute("numOfTickets", numTickets);

        // Return booking confirmation view
        return "bookingConfirmation";
    }
}
