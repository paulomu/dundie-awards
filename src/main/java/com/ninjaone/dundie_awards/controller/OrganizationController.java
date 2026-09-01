package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.model.Organization;
import com.ninjaone.dundie_awards.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // get all organizations
    @GetMapping("/organizations")
    @ResponseBody
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // create organization rest api
    @PostMapping("/organizations")
    @ResponseBody
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    // get organization by id rest api
    @GetMapping("/organizations/{id}")
    @ResponseBody
    public Organization getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    // update organization rest api
    @PutMapping("/organizations/{id}")
    @ResponseBody
    public Organization updateOrganization(@PathVariable Long id, @RequestBody Organization organizationDetails) {
        return organizationService.updateOrganization(id, organizationDetails);
    }

    // delete organization rest api
    @DeleteMapping("/organizations/{id}")
    @ResponseBody
    public Map<String, Boolean> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return Map.of("deleted", Boolean.TRUE);
    }
}
