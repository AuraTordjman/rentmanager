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
import java.time.format.DateTimeParseException;

@WebServlet("/vehicles/edit")
public class VehicleEditServlet extends HttpServlet {

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleService = new VehicleService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            //System.out.println(clientId);
            Vehicle vehicle = vehicleService.findById(vehicleId);
            //System.out.println(client);
            request.setAttribute("vehicle", vehicle);

            request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            String constructeur = request.getParameter("constructeur");
            String modele= request.getParameter("modele");
            int nb_places = Integer.parseInt(request.getParameter("nb_places"));
            //System.out.println(request.getParameter("naissance"));

            Vehicle updatedVehicle = new Vehicle(vehicleId, constructeur, modele, nb_places);
            System.out.println(updatedVehicle);
            vehicleService.update(updatedVehicle);

            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (NumberFormatException | DateTimeParseException | ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
