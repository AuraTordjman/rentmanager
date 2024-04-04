package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/vehicles/delete")
public class VehicleDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer l'identifiant du véhicule à supprimer depuis les paramètres de la requête
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));

            // Appel à la méthode delete du service pour supprimer le véhicule
            VehicleService vehicleService = VehicleService.getInstance();
            vehicleService.delete(vehicleId);

            // Redirection vers la liste des véhicules après la suppression
            response.sendRedirect(request.getContextPath() + "/vehicles");

        } catch (ServiceException | NumberFormatException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
