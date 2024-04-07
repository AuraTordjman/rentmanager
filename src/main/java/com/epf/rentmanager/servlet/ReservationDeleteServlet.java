package com.epf.rentmanager.servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            long reservationId = Long.parseLong(request.getParameter("reservationId"));
            reservationService.delete(reservationId);

            response.sendRedirect(request.getContextPath() + "/rents");

        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
