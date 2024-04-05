package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {

    private final ClientService clientService = ClientService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("reservationId"));
            Reservation reservation = reservationService.findById(reservationId);
            Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
            Client client = clientService.findById(reservation.getClient_id());

            request.setAttribute("vehicle", vehicle);
            request.setAttribute("client", client);
            request.setAttribute("reservation", reservation);

            request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du client", e);
        }
    }
}