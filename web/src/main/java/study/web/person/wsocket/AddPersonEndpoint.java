package study.web.person.wsocket;

import lombok.extern.slf4j.Slf4j;
import study.web.person.facade.PersonFacade;
import study.web.person.facade.dto.NewPersonCommandDTO;
import study.web.person.facade.dto.PersonDTO;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@Slf4j
@ServerEndpoint(
        value = "/socket/persons/add",
        encoders = {PersonDTOJsonEncoder.class, PersonDTOJsonArrayEncoder.class},
        decoders = {NewPersonCommandDTOJsonDecoder.class}
)
public class AddPersonEndpoint {

    @Inject
    private PersonFacade personFacade;
    @Inject
    private SharedSessions sharedSessions;


    @OnMessage
    public void onMessage(Session session, NewPersonCommandDTO message) {
        log.info("Message received!");
        PersonDTO saved = personFacade.save(message);
        //Send to all sessions a new Person
        final List<Session> openedSessions = sharedSessions.getListSessions();
        for (Session s : openedSessions) {
            try {
                final List<PersonDTO> dtoList = personFacade.list();
                s.getBasicRemote().sendObject(dtoList);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

}
