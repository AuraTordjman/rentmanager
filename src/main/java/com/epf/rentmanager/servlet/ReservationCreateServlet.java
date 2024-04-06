package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")


public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> listeDesVoitures = null;
        List<Client> listeDesClients = null;

        try {
            listeDesVoitures = vehicleService.findAll();
            listeDesClients = clientService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        // Stocker la liste des voitures dans un attribut de la requête
        request.setAttribute("listeDesVoitures", listeDesVoitures);
        request.setAttribute("listeDesClients", listeDesClients);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            // Utiliser un DateTimeFormatter pour définir le format de date attendu
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            int car = Integer.parseInt(request.getParameter("car"));
            int client = Integer.parseInt(request.getParameter("client"));

            // Parser la chaîne de date reçue depuis le formulaire
            LocalDate begin = LocalDate.parse(request.getParameter("begin"), formatter);
            LocalDate end = LocalDate.parse(request.getParameter("end"), formatter);

            // Création d'un objet Vehicle avec un ID par défaut (-1)
           Reservation newReservation = new Reservation(-1, client, car, begin , end);

            // Appel à la méthode create du service
            reservationService.create(newReservation);

            // Redirection vers la liste des clients
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
        }
    }
}