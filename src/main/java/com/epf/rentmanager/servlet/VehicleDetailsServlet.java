package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {

    @Autowired
    private VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            Vehicle vehicle = vehicleService.findById(vehicleId);

            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du véhicule", e);
        }
    }
}
