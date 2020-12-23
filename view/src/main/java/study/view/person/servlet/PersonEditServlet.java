package study.view.person.servlet;

import lombok.extern.slf4j.Slf4j;
import study.facade.person.PersonFacade;
import study.facade.person.dto.EditPersonCommandDTO;
import study.facade.person.dto.PersonDTO;
import study.view.exception.PageNotFoundException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMINISTRATOR", "LIST_PERSON"}))
@WebServlet(value = "/secure/person/edit")
public class PersonEditServlet extends HttpServlet {

    @EJB
    private PersonFacade personFacade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) throw new PageNotFoundException();

        PersonDTO person = personFacade.get(Long.valueOf(id));
        if (person != null) {
            req.setAttribute("person", person);
            req.getRequestDispatcher("/secure/person/edit.jsp")
                    .forward(req, resp);
        } else {
            throw new PageNotFoundException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) throw new PageNotFoundException();

        EditPersonCommandDTO command = new EditPersonCommandDTO();
        command.setId(Long.valueOf(id));
        command.setName(req.getParameter("name"));
        command.setSurname(req.getParameter("surname"));
        command.setEmail(req.getParameter("email"));
        command.setVersion(Integer.valueOf(req.getParameter("version")));

        String birthDate = req.getParameter("birthDate");
        if(birthDate != null) {
            log.info("birthDate: " + birthDate);
            try {
                Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
                command.setBirthDate(parse);
            } catch (ParseException ignore) {
            }
        }
        PersonDTO person  = personFacade.edit(command);
        req.setAttribute("person", person);
        req.getRequestDispatcher("/secure/person/edit.jsp")
                .forward(req, resp);
    }
}

