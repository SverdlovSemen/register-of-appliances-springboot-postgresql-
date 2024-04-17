package ru.sverdlov.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sverdlov.app.dto.TechnicDTO;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.EntityValidator;
import ru.sverdlov.app.services.TechnicService;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TechnicControllerTest {
    @Mock
    private TechnicService technicService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EntityValidator entityValidator;

    @InjectMocks
    private TechnicController technicController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private Technic technic1;
    private Technic technic2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(technicController).build();
        objectMapper = new ObjectMapper();
        technic1 = new Technic(1, "Headphones", "Britain", "Marshall");
        technic2 = new Technic(2, "Keyboard", "Switzerland", "Logitech");
    }

    @Test
    void getAll() throws Exception {
        when(technicService.findAll()).thenReturn(List.of(technic1, technic2));

        when(modelMapper.map(technic1, TechnicDTO.class)).thenReturn(new TechnicDTO("Headphones",
                "Britain", "Marshall", true, true));
        when(modelMapper.map(technic2, TechnicDTO.class)).thenReturn(new TechnicDTO("Keyboard",
                "Switzerland", "Logitech", true, false));

        mockMvc.perform(get("/technics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Headphones"))
                .andExpect(jsonPath("$[0].countryOfOrigin").value("Britain"))
                .andExpect(jsonPath("$[0].manufacturer").value("Marshall"))
                .andExpect(jsonPath("$[0].isPossibleOrderOnline").value(true))
                .andExpect(jsonPath("$[0].isPossibleMakeInstallments").value(true))

                .andExpect(jsonPath("$[1].name").value("Keyboard"))
                .andExpect(jsonPath("$[1].countryOfOrigin").value("Switzerland"))
                .andExpect(jsonPath("$[1].manufacturer").value("Logitech"))
                .andExpect(jsonPath("$[1].isPossibleOrderOnline").value(true))
                .andExpect(jsonPath("$[1].isPossibleMakeInstallments").value(false));

        verify(technicService, times(1)).findAll();
    }

    @Test
    void getById() throws Exception {
        when(technicService.findOne(8)).thenReturn(technic1);

        when(modelMapper.map(technic1, TechnicDTO.class)).thenReturn(new TechnicDTO("Headphones",
                "Britain", "Marshall", true, true));

        mockMvc.perform(get("/technics/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Headphones"))
                .andExpect(jsonPath("$.countryOfOrigin").value("Britain"))
                .andExpect(jsonPath("$.manufacturer").value("Marshall"));

        verify(technicService, times(1)).findOne(8);
    }

    @Test
    void create_validTechnicDTO_returnsOkStatus() throws Exception{
        doNothing().when(technicService).save(any());

        TechnicDTO technicDTO = new TechnicDTO("Headphones", "Britain", "Marshall",
                true, true);

        String technicJson = objectMapper.writeValueAsString(technicDTO);

        mockMvc.perform(post("/technics/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technicJson))
                .andExpect(status().isOk());

        verify(technicService, times(1)).save(any());
    }

    @Test
    void create_invalidTechnicDTO_throwsEntityNotCreatedException() throws Exception {
        TechnicDTO invalidTechnicDTO = new TechnicDTO("", "", "", null, null);

        String technicJson = objectMapper.writeValueAsString(invalidTechnicDTO);

        mockMvc.perform(post("/technics/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technicJson))
                .andExpect(status().isBadRequest());

        verify(technicService, times(0)).save(any());
    }
}


























