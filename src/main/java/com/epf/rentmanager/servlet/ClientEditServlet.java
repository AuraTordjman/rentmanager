package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {

    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        clientService = new ClientService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int clientId = Integer.parseInt(request.getParameter("clientId"));
            Client client = clientService.findById(clientId);
            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données du formulaire
            String email = request.getParameter("email");
            String naissanceParam = request.getParameter("naissance");
            if (naissanceParam == null) {
                // Gérer le cas où la date de naissance est manquante
                // Vous pouvez rediriger vers une page d'erreur ou afficher un message à l'utilisateur
                return;
            }
            LocalDate naissance = LocalDate.parse(naissanceParam);

            int clientId = Integer.parseInt(request.getParameter("clientId"));
            if (clientId <= 0) {
                // Gérer le cas où l'identifiant du client est invalide
                // Vous pouvez rediriger vers une page d'erreur ou afficher un message à l'utilisateur
                return;
            }

            // Mettre à jour l'utilisateur avec les nouvelles données
            ClientService clientService = new ClientService();

            // Récupérer l'utilisateur existant pour obtenir son nom et prénom
            Client existingUser = clientService.findById(clientId);
            if (existingUser == null) {
                // Gérer le cas où le client n'existe pas
                // Vous pouvez rediriger vers une page d'erreur ou afficher un message à l'utilisateur
                return;
            }
            String nom = existingUser.getNom();
            String prenom = existingUser.getPrenom();

            // Créer un objet Client avec les nouvelles données et les données existantes
            Client updatedClient = new Client(clientId, nom, prenom, email, naissance);

            // Appeler la méthode update de ClientService pour mettre à jour l'utilisateur
            clientService.update(updatedClient);

            // Rediriger vers la liste des utilisateurs après la mise à jour
            response.sendRedirect(request.getContextPath() + "/users/list");
        } catch (Exception e) {
            e.printStackTrace(); // Affichez la stack trace complète de l'exception pour faciliter le débogage
            // Gérer l'exception, par exemple en affichant un message d'erreur à l'utilisateur
        }
    }


}
