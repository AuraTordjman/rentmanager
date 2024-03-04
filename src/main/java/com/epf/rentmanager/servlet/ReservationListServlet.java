package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReservationService reservationService;

    public ReservationListServlet() {
        this.reservationService = ReservationService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reservation> reservations = reservationService.findAll();
            request.setAttribute("rents", reservations);
            request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Erreur lors de la récupération de la liste des réservations.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
