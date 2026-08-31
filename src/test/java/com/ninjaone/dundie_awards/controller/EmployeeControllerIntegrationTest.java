package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.model.Employee;
import com.ninjaone.dundie_awards.model.Organization;
import com.ninjaone.dundie_awards.repository.EmployeeRepository;
import com.ninjaone.dundie_awards.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        var organization = new Organization();
        organization.setId(1L);
        organization.setName("NinjaOne");
        organization = organizationRepository.save(organization);

        var employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Peter");
        employee.setLastName("Parker");
        employee.setDundieAwards(1);
        employee.setOrganization(organization);
        employeeRepository.save(employee);
    }

    /* GET /employees */
    @Test
    public void shouldReturnAllEmployeeDetailsWhenGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk());
    }

    /* POST /employees */
    @Test
    public void shouldReturnCreatedEmployeeDetailsWhenCreateEmployee() throws Exception {
        final var request = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "dundieAwards": 0,
            "organization": {
                "id": 1,
                "name": "NinjaOne"
            }
        }
        """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    /* GET /employees/{id} */
    @Test
    public void shouldReturnEmployeeDetailsWhenGetEmployeeById() throws Exception {
        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("firstName").value("Peter"))
                .andExpect(jsonPath("lastName").value("Parker"))
                .andExpect(jsonPath("dundieAwards").value(1L))
                .andExpect(jsonPath("organization.id").value(1L))
                .andExpect(jsonPath("organization.name").value("NinjaOne"));
    }

    @Test
    public void shouldReturn404WhenGetEmployeeByIdNotFound() throws Exception {
        mockMvc.perform(get("/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    /* PUT /employees/{id} */
    @Test
    public void shouldReturnUpdatedEmployeeDetailsWhenUpdateEmployeeById() throws Exception {
        final var request = """
        {
            "firstName": "Spider",
            "lastName": "Man"
        }
        """;

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("Spider"))
                .andExpect(jsonPath("lastName").value("Man"));
    }

    @Test
    public void shouldReturn404WhenUpdateEmployeeByIdNotFound() throws Exception {
        final var request = """
        {
            "firstName": "Spider",
            "lastName": "Man"
        }
        """;

        mockMvc.perform(put("/employees/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    /* DELETE /employees/{id} */
    @Test
    public void shouldReturnDeletedEmployeeDetailsWhenDeleteEmployeeById() throws Exception {
        mockMvc.perform(delete("/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deleted").value(true));
    }

    @Test
    public void shouldReturn404WhenDeleteEmployeeByIdNotFound() throws Exception {
        mockMvc.perform(delete("/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
