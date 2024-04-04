package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {
    private final VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            Vehicle vehicle = vehicleService.findById(vehicleId);

            // Transfert des détails du véhicule à la page JSP
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du véhicule", e);
        }
    }
}
