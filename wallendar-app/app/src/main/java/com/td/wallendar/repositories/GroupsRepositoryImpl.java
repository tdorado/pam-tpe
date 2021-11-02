package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.mappers.GroupMapper;
import com.td.wallendar.service.GroupsService;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public class GroupsRepositoryImpl implements GroupsRepository {

    public GroupsService groupsService;

    public GroupsRepositoryImpl() {
        this.groupsService = ServiceModule.getRetrofit().create(GroupsService.class);
    }

    @Override
    public Observable<List<Group>> getGroupsByUser(final Long userId) {
        return groupsService.getGroupsByUserId(userId).map(GroupMapper::toModel);
    }

    @Override
    public Observable<Group> getGroup(final Long groupId) {
        return groupsService.getGroupById(groupId).map(GroupMapper::toModel);
    }

    @Override
    public Observable<Group> getGroupById(Long groupId) {
        return null;
    }

    @Override
    public Observable<Group> createGroup(String groupTitle, Long ownerId) {
        return groupsService.createGroup(new AddGroupRequest(groupTitle, ownerId)).map(GroupMapper::toModel);
    }

    @Override
    public Observable<String> addMembers(Set<String> members) {
        return null;
    }

    @Override
    public Observable<String> addCharge(Long groupId, Long ownerId, String description, double amount) {
        return null;
    }

    @Override
    public Observable<String> addPayment(Long groupId, Long ownerId, double amount) {
        return null;
    }
}
