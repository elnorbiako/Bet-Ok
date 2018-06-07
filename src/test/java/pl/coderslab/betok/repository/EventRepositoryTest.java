package pl.coderslab.betok.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.betok.entity.Event;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldFindRandomEvent() {

        //given
        Event event1 = new Event();
        event1.setId(1L);
        entityManager.persist(event1);
        //when
        Event result = eventRepository.findRandomEvent();
        //then
        assertEquals(result.getId(), event1.getId());

    }

    @Test
    public void ShouldFind10WithFTStatus() {

        //given

        for (long i = 1; i<20; i++){
            Event event = new Event();
            event.setId(i);
            event.setStatus("FT");
            entityManager.persist(event);
        }
        //when
        List<Event> result = eventRepository.findTop10ByStatusOrderByDateAsc("FT");
        //then
        assertEquals(result.size(), 10);
        assertEquals(result.get(5).getStatus(), "FT");

    }

    @Test
    public void ShouldNotWithSCHEDULEDStatus() {

        //given

        for (long i = 1; i<30; i++){
            Event event = new Event();
            event.setId(i);
            event.setStatus("FT");
            entityManager.persist(event);
        }
        //when
        List<Event> result = eventRepository.findTop10ByStatusOrderByDateAsc("SCHEDULED");
        //then
        assertEquals(result.size(), 0);


    }

    @Test
    public void ShouldFetchOneWithSCHEDULEDStatus() {

        //given

        Event eventSCH = new Event();
        eventSCH.setId(50);
        eventSCH.setStatus("SCHEDULED");
        entityManager.persist(eventSCH);

        for (long i = 1; i<30; i++){
            Event event = new Event();
            event.setId(i);
            event.setStatus("FT");
            entityManager.persist(event);
        }
        //when
        List<Event> result = eventRepository.findTop10ByStatusOrderByDateAsc("SCHEDULED");
        //then
        assertEquals(result.size(), 1);


    }
}