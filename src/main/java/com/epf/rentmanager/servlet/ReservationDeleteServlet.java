
package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer l'identifiant  à supprimer depuis les paramètres de la requête
            long reservationId = Long.parseLong(request.getParameter("reservationId"));

            // Appel à la méthode delete du service pour supprimer
           ReservationService reservationService = ReservationService.getInstance();
            reservationService.delete(reservationId);

            // Redirection vers la liste après la suppression
            response.sendRedirect(request.getContextPath() + "/rents");

        } catch (ServiceException | NumberFormatException e) {
            // Gérer l'exception (par exemple, rediriger vers une page d'erreur)
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
