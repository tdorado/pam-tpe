package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.dtos.response.GroupsResponse;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.repositories.mappers.GroupsMapper;
import com.td.wallendar.service.GroupsService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class GroupsRepositoryImpl implements GroupsRepository {

    public GroupsService groupsService;

    public GroupsRepositoryImpl() {
        this.groupsService = ServiceModule.getGroupsService();;
    }

    @Override
    public List<Group> getGroupsByUser(final Long userId) {
        try {
            Response<GroupsResponse> response = groupsService.getGroupsByUserId(userId).execute();
            if(response.isSuccessful() && response.body() != null){
                return GroupsMapper.toModel(response.body());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group getGroup(final Long groupId) {
        return new Group();
    }
}
