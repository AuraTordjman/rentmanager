package com.epf.rentmanager.servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/vehicles/create")


public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }

     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nbPlaces = Integer.parseInt(request.getParameter("nb_places"));
            Vehicle newVehicle = new Vehicle(-1, constructeur, modele, nbPlaces);
            vehicleService.create(newVehicle);

            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
