package com.ninjaone.dundie_awards.service;

import com.ninjaone.dundie_awards.exception.OrganizationNotFoundException;
import com.ninjaone.dundie_awards.model.Activity;
import com.ninjaone.dundie_awards.model.Organization;
import com.ninjaone.dundie_awards.repository.ActivityRepository;
import com.ninjaone.dundie_awards.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization createOrganization(Organization organization) {
        activityRepository.save(Activity.organizationCreated(organization));
        return organizationRepository.save(organization);
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    public Organization updateOrganization(Long id, Organization organizationDetails) {
        final var organization = getOrganizationById(id);
        final var activity = Activity.organizationUpdated(organization, organizationDetails);
        organization.setName(organizationDetails.getName());
        activityRepository.save(activity);
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        final var organization = getOrganizationById(id);
        activityRepository.save(Activity.organizationDeleted(organization));
        organizationRepository.delete(organization);
    }
}
