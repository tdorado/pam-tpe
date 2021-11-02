package com.td.wallendar.repositories;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.mappers.GroupMapper;
import com.td.wallendar.utils.networking.RetrofitUtils;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;

public class GroupsRepositoryImpl implements GroupsRepository {

    public final GroupsService groupsService;

    public GroupsRepositoryImpl(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @Override
    public Single<List<Group>> getGroupsByUser(final Long userId) {
        return RetrofitUtils.performRequest(groupsService.getGroupsByUserId(userId)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> getGroup(final Long groupId) {
        return RetrofitUtils.performRequest(groupsService.getGroupById(groupId)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> createGroup(String groupTitle, Long ownerId) {
        return RetrofitUtils.performRequest(groupsService.createGroup(new AddGroupRequest(groupTitle, ownerId))).map(GroupMapper::toModel);
    }

    @Override
    public Single<String> addMembers(Set<String> members) {
        return null;
    }

    @Override
    public Single<String> addCharge(Long groupId, Long ownerId, String description, double amount) {
        return null;
    }

    @Override
    public Single<String> addPayment(Long groupId, Long ownerId, double amount) {
        return null;
    }
}
