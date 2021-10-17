package com.td.wallendarbackend.controllers;

import com.td.wallendarbackend.dtos.requests.AddMembersRequest;
import com.td.wallendarbackend.dtos.requests.GroupRequest;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody @Valid GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        if(group != null) {
            return new ResponseEntity<>(group, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        GroupResponse group = groupService.findById(id);
        if(group != null){
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/allFromUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getGroupsByApplicationUserId(@PathVariable Long id) {
        List<GroupResponse> groups = groupService.findGroupsByApplicationUserId(id);
        if(groups != null) {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/addMembers")
    @ResponseBody
    public ResponseEntity<?> getGroupById(@PathVariable Long id, @RequestBody @Valid AddMembersRequest addMembersRequest) {
        GroupResponse group = groupService.addMembers(id, addMembersRequest);
        if(group != null){
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
