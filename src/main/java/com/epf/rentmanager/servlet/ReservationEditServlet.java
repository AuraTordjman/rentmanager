package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Vehicle> listeDesVoitures = new ArrayList<>();
            List<Client> listeDesClients = new ArrayList<>();

            listeDesVoitures = vehicleService.findAll();

            listeDesClients = clientService.findAll();
            int reservationId = Integer.parseInt(request.getParameter("id"));

            Reservation reservation = reservationService.findById(reservationId);
            request.setAttribute("reservation", reservation);
            request.setAttribute("listeDesVoitures", listeDesVoitures);
            request.setAttribute("listeDesClients", listeDesClients);
            request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            int car = Integer.parseInt(request.getParameter("vehicle"));
            int client = Integer.parseInt(request.getParameter("client"));
            System.out.println(car);
            System.out.println(client);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate begin = LocalDate.parse(request.getParameter("begin"), formatter);
            LocalDate end = LocalDate.parse(request.getParameter("end"), formatter);
            Reservation updatedReservation = new Reservation(reservationId, car, client, begin, end);
            reservationService.update(updatedReservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (NumberFormatException | DateTimeParseException | ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
