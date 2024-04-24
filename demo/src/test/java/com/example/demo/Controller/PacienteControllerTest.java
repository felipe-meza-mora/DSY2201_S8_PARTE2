package com.example.demo.Controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.controller.PacienteController;
import com.example.demo.model.Paciente;
import com.example.demo.service.PacienteServiceImpl;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteServiceImpl pacienteServiceMock;

    public void obtenerTodosTest() throws Exception{
        //ARRANGE
        //CREACION DE PACIENTES
        Paciente paciente1 = new Paciente();
        paciente1.setId(1);
        paciente1.setRut("12345678-9");
        paciente1.setNombreCompleto("Juan Perez");
        paciente1.setFechaNacimiento(LocalDate.of(1990, 12, 1));

        Paciente paciente2 = new Paciente();
        paciente2.setId(2);
        paciente2.setRut("33333333-3");
        paciente2.setNombreCompleto("Pedro Perez");
        paciente2.setFechaNacimiento(LocalDate.of(1990, 11, 1));

        //CREACION DE LISTA DE PACIENTES
        List<Paciente> listaPacientes = Arrays.asList(paciente1,paciente2);
        when(pacienteServiceMock.getAllPacientes()).thenReturn(listaPacientes);
        
        //ACT & ASSERT
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[0].rut",Matchers.is("12345678-9")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[0].nombreCompleto",Matchers.is("Juan Perez")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[0].fechaNacimiento",Matchers.is("1990-12-01")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[1].rut",Matchers.is("33333333-3")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[1].nombreCompleto",Matchers.is("Pedro Perez")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[1].fechaNacimiento",Matchers.is("1990-11-01")));
    }

    
    
}
