package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/users/create")


public class ClientCreateServlet extends HttpServlet {
    //private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));


            // Création d'un objet Vehicle avec un ID par défaut (-1)
            Client newClient = new Client(-1, nom , prenom, email, naissance);

            // Appel à la méthode create du service
            ClientService clientService = ClientService.getInstance();
            long generatedId = clientService.create(newClient);

            // Mise à jour de l'ID après la création
            newClient.setId((int) generatedId);

            // Mise à jour du nombre de véhicules dans la session
            int numberOfClients = clientService.countClients();
            request.getSession().setAttribute("numberOfClients", numberOfClients);

            // Récupérer la liste des véhicules mise à jour
            List<Client> clients = clientService.findAll();

            // Ajouter le nouveau véhicule à la liste
           clients.add(newClient);

            // Mettre à jour l'attribut de la requête avec la liste mise à jour
            request.setAttribute("clients", clients);

            // Redirection vers la liste des clients
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException | DaoException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
        }
    }
}


