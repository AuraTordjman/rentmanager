package com.ensta.rentmanager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientDao clientDao; // On mocke le DAO
    @InjectMocks
    private ClientService clientService; // On injecte le mock dans le service à tester

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {

        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void findById_if_correctUser() throws DaoException, ServiceException {

        Client clientATester = new Client(1, "Tordjman", "Aura", "tordjmanaura@gmail.com", LocalDate.of(2000, 11, 2));

        when(clientDao.findById(1)).thenReturn(clientATester);

        Client clientRef = clientService.findById(1);

        assertEquals(clientATester, clientRef);
        verify(clientDao, times(1)).findById(1);
    }
    @Test
    public void testCreateClient_age() throws ServiceException, DaoException {

        Client client = new Client(1, "Tordjman", "Aura", "tordjmanaura@gmail.com", LocalDate.of(2020, 11, 2));


        when(clientDao.findByEmail(anyString())).thenReturn(null);
        when(clientDao.create(client)).thenReturn(1L);


        long clientId = clientService.create(client);


        assertEquals(1L, clientId);
        verify(clientDao, times(1)).create(client);
    }

}
