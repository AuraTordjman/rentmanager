package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;

    @InjectMocks
    private ReservationService reservationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReservation() throws ServiceException, DaoException {

        Reservation reservation = new Reservation();
        reservation.setVehicle_id(1);
        reservation.setDebut(LocalDate.of(2024, 4, 10));
        reservation.setFin(LocalDate.of(2024, 4, 15));


        when(reservationDao.isVehicleReservedForThirtyDaysOrMore(1)).thenReturn(false);
        when(reservationDao.findByVehicleAndDates(1, LocalDate.of(2024, 4, 10), LocalDate.of(2024, 4, 15)))
                .thenReturn(Collections.emptyList());
        when(reservationDao.create(reservation)).thenReturn(1L);


        long reservationId = reservationService.create(reservation);

        assertTrue(reservationId > 0);
        verify(reservationDao, times(1)).create(reservation);
    }

    @Test(expected = ServiceException.class)
    public void testCreateReservationWithInvalidDates() throws ServiceException, DaoException {

        Reservation reservation = new Reservation();
        reservation.setVehicle_id(1);
        reservation.setDebut(LocalDate.of(2024, 4, 15));
        reservation.setFin(LocalDate.of(2024, 4, 10));


        reservationService.create(reservation);
    }

}
