package com.ensta.rentmanager;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class VehicleServiceTest {

    @Mock
    private VehicleDao vehicleDao;

    @InjectMocks
    private VehicleService vehicleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateVehicle_NbPlaces_invalide() throws ServiceException, DaoException {

        Vehicle vehicle = new Vehicle(1, "Toyota", "Corolla", 0);
        when(vehicleDao.create(vehicle)).thenReturn(1L);


        long vehicleId = vehicleService.create(vehicle);


        assertEquals(1L, vehicleId);
    }

    @Test(expected = ServiceException.class)
    public void testCreateVehicleWithout_constructeur() throws ServiceException, DaoException {

        Vehicle vehicle = new Vehicle(1, "", "Corolla", 1);


        vehicleService.create(vehicle);

    }
}
