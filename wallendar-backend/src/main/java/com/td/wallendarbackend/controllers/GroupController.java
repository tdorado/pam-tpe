package com.td.wallendarbackend.controllers;

import com.td.wallendarbackend.dtos.requests.GroupRequest;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        groupService.createGroup(groupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        Group group = groupService.findById(id);
        if(group != null){
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/allFromUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getGroupsByApplicationUserId(@PathVariable Long id) {
        List<Group> groups = groupService.findGroupsByApplicationUserId(id);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

}
