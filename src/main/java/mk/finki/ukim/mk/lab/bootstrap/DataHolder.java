package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder
{
    public static List<Event> events=new ArrayList<>();
    public static List<Location> locations=new ArrayList<>();

    @PostConstruct
    public void init()
    {
        locations.add(new Location("Skopje","fdusihfjds","hdsafas","hasf"));
        locations.add(new Location("fjdshfjds","fdusihfjds","hdsafas","hasf"));
        locations.add(new Location("fjaskhasf","fdusihfjds","hdsafas","hasf"));

        events.add(new Event("Pivofest","Pivo",10.0, locations.get(0)));
        events.add(new Event("Vinoskop","vino",8.0, locations.get(2)));
        events.add(new Event("Primatijada","pienje",10.0, locations.get(1)));
        events.add(new Event("hasdas","sajkfhksf",4.0, locations.get(1)));
        events.add(new Event("ne znam","fhasdkf",3.9, locations.get(1)));
    }
}
