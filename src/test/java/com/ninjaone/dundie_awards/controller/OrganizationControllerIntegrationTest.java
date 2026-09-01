package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.model.Organization;
import com.ninjaone.dundie_awards.repository.ActivityRepository;
import com.ninjaone.dundie_awards.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class OrganizationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        var organization = new Organization();
        organization.setId(1L);
        organization.setName("NinjaOne");
        organizationRepository.save(organization);
    }

    /* GET /organizations */
    @Test
    public void shouldReturnAllOrganizationDetailsWhenGetAllOrganizations() throws Exception {
        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk());
    }

    /* POST /organizations */
    @Test
    public void shouldReturnCreatedOrganizationDetailsWhenCreateOrganization() throws Exception {
        final var request = """
        {
            "name": "Dunder Mifflin"
        }
        """;

        mockMvc.perform(post("/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists());

        var activities = activityRepository.findAll();
        assertThat(activities.size()).isEqualTo(1);
        assertThat(activities.get(0).getEvent()).isEqualTo("Created organization Dunder Mifflin");
    }

    /* GET /organizations/{id} */
    @Test
    public void shouldReturnOrganizationDetailsWhenGetOrganizationById() throws Exception {
        mockMvc.perform(get("/organizations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("NinjaOne"));
    }

    @Test
    public void shouldReturn404WhenGetOrganizationByIdNotFound() throws Exception {
        mockMvc.perform(get("/organizations/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Organization not found: 999"));
    }

    /* PUT /organizations/{id} */
    @Test
    public void shouldReturnUpdatedOrganizationDetailsWhenUpdateOrganizationById() throws Exception {
        final var request = """
        {
            "name": "Ninja One"
        }
        """;

        mockMvc.perform(put("/organizations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Ninja One"));

        var activities = activityRepository.findAll();
        assertThat(activities.size()).isEqualTo(1);
        assertThat(activities.get(0).getEvent()).isEqualTo("Updated organization NinjaOne to Ninja One");
    }

    @Test
    public void shouldReturn404WhenUpdateOrganizationByIdNotFound() throws Exception {
        final var request = """
        {
            "name": "Ninja One"
        }
        """;

        mockMvc.perform(put("/organizations/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Organization not found: 999"));
    }

    /* DELETE /organizations/{id} */
    @Test
    public void shouldReturnDeletedOrganizationDetailsWhenDeleteOrganizationById() throws Exception {
        mockMvc.perform(delete("/organizations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deleted").value(true));

        var activities = activityRepository.findAll();
        assertThat(activities.size()).isEqualTo(1);
        assertThat(activities.get(0).getEvent()).isEqualTo("Deleted organization NinjaOne");
    }

    @Test
    public void shouldReturn404WhenDeleteOrganizationByIdNotFound() throws Exception {
        mockMvc.perform(delete("/organizations/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Organization not found: 999"));
    }
}
