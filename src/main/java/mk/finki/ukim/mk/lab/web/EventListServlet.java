package mk.finki.ukim.mk.lab.web;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.impl.EventServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@WebServlet(name = "EventListServlet", urlPatterns = "/events/eventList")
public class EventListServlet extends HttpServlet {

    private final SpringTemplateEngine templateEngine;
    private final EventServiceImpl eventService;

    public EventListServlet(SpringTemplateEngine templateEngine, EventServiceImpl eventService) {
        this.templateEngine = templateEngine;
        this.eventService = eventService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events;
        String searchName = req.getParameter("searchName");
        String minRating = req.getParameter("minRating");

        if (searchName != null && minRating != null && !Objects.equals(minRating, "")) {
            events = eventService.searchEvents(searchName).stream()
                    .filter(event -> event.getPopularityScore() >= Double.parseDouble(minRating))
                    .toList();
        } else if (minRating != null && !Objects.equals(minRating, "")) {
            events = eventService.listAll().stream()
                    .filter(event -> event.getPopularityScore() >= Double.parseDouble(minRating))
                    .toList();
        } else if (searchName != null) {
            events = eventService.searchEvents(searchName);
        } else {
            events = eventService.listAll();
        }

        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);
        context.setVariable("events", events);
        templateEngine.process("listEvents.html", context, resp.getWriter());
    }
}
