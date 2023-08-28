package com.fbl.app.children.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.openapi.TagChildren;
import com.fbl.app.children.service.ChildrenService;
import com.fbl.app.children.service.ManageChildrenService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * Children Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/children")
@RestApiController
@RequiredArgsConstructor
@TagChildren
public class ChildrenController {

    private final ChildrenService childrenService;
    private final ManageChildrenService manageChildrenService;

    /**
     * Gets a list of children based of the request filter
     * 
     * @param request to filter on
     * @return list of child objects
     */
    @Operation(summary = "Get a list of children.", description = "Given a Child Get Request, it will return a list of children that match the request.")
    @GetMapping
    public Page<Child> getChildren(ChildGetRequest request) {
        return childrenService.getChildren(request);
    }

    /**
     * Gets a child by id
     * 
     * @param id The child id
     * @return The found child based on the id
     */
    @Operation(summary = "Get a child by id", description = "Given a child id, it will return that child information if found.")
    @GetMapping("/{id}")
    public Child getChildById(@PathVariable int id) {
        return childrenService.getChildById(id);
    }

    /**
     * Gets gurdian children by gurdian id.
     * 
     * @param gurdianId The gurdian id
     * @return The list of children associated to the gurdian
     */
    @Operation(summary = "Get list of gurdian children", description = "Given a gurdian id, it will get the list of children for that gurdian.")
    @GetMapping("/gurdian/{gurdianId}")
    public List<Child> getGurdianChildren(@PathVariable int gurdianId) {
        return childrenService.getGurdianChildren(gurdianId);
    }

    /**
     * Creates a new child for the given object.
     * 
     * @param child The child to create.
     * @return {@link Child} that was created.
     */
    @Operation(summary = "Create a new child", description = "Given a child request body. It will create a new child.")
    @PostMapping
    @HasAccess(WebRole.LEADER)
    public Child insertChild(@RequestBody @Valid Child child) {
        return manageChildrenService.insertChild(child);
    }

    /**
     * Update the child's information such as email, first name, and last name
     * 
     * @param child what information on the child needs to be updated.
     * @return child associated to that id with the updated information
     */
    @Operation(summary = "Update child Information", description = "Will update the given child information.")
    @PutMapping("/{id}")
    @HasAccess(WebRole.WORKER)
    public Child updateChildById(@PathVariable int id, @RequestBody Child child) {
        return manageChildrenService.updateChildById(id, child);
    }

    /**
     * Update child groups by id.
     * 
     * @param id    The id of the child to be updated
     * @param group The list of groups to assign to the child
     * @return The updated child with the new groups.
     */
    @Operation(summary = "Update child church groups by id ", description = "Will update the childs church groups.")
    @PutMapping("/{id}/groups")
    @HasAccess(WebRole.WORKER)
    public Child updateChildGroupsById(@PathVariable int id, @RequestBody List<ChurchGroup> groups) {
        return manageChildrenService.updateChildGroupsById(id, groups);
    }

    /**
     * Delete a child by id.
     * 
     * @param Id The child id to delete.
     */
    @Operation(summary = "Delete a child.", description = "Delete a child for the given id.")
    @DeleteMapping("/{childId}")
    @HasAccess(WebRole.LEADER)
    public void deleteChild(@PathVariable int childId) {
        manageChildrenService.deleteChild(childId);
    }
}
