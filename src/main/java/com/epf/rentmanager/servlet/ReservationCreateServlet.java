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
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/rents/create")


public class ReservationCreateServlet extends HttpServlet {
    //private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> listeDesVoitures = null;
        try {
            listeDesVoitures = VehicleService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        // Stocker la liste des voitures dans un attribut de la requête
        request.setAttribute("listeDesVoitures", listeDesVoitures);
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
            ReservationService reservationService = ReservationService.getInstance();
            long generatedId = reservationService.create(newReservation);

            // Mise à jour de l'ID après la création
            newReservation.setId((int) generatedId);

            // Mise à jour du nombre de véhicules dans la session
            int numberOfReservations = reservationService.countReservations();
            request.getSession().setAttribute("numberOfReservations", numberOfReservations);

            // Récupérer la liste des véhicules mise à jour
            List<Reservation> reservations = reservationService.findAll();

            // Ajouter le nouveau véhicule à la liste
            reservations.add(newReservation);

            // Mettre à jour l'attribut de la requête avec la liste mise à jour
            request.setAttribute("reservations", reservations);

            // Redirection vers la liste des clients
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | DaoException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
        }
    }
}