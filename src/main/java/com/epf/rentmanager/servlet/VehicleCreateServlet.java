package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/vehicles/create")


public class VehicleCreateServlet extends HttpServlet {
    //private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }

     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nbPlaces = Integer.parseInt(request.getParameter("nb_places"));

            // Création d'un objet Vehicle avec un ID par défaut (-1)
            Vehicle newVehicle = new Vehicle(-1, constructeur, modele, nbPlaces);

            // Appel à la méthode create du service
            VehicleService vehicleService = VehicleService.getInstance();
            long generatedId = vehicleService.create(newVehicle);

            // Mise à jour de l'ID après la création
            newVehicle.setId((int) generatedId);

            // Mise à jour du nombre de véhicules dans la session
            int numberOfVehicles = vehicleService.countVehicles();
            request.getSession().setAttribute("numberOfVehicles", numberOfVehicles);

            // Récupérer la liste des véhicules mise à jour
            List<Vehicle> vehicles = VehicleService.findAll();

            // Ajouter le nouveau véhicule à la liste
            vehicles.add(newVehicle);

            // Mettre à jour l'attribut de la requête avec la liste mise à jour
            request.setAttribute("vehicles", vehicles);

            // Redirection vers la liste des clients
            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (ServiceException | DaoException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
        }
    }
}
