package com.td.wallendar.repositories;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.dtos.request.AddMemberByEmailRequest;
import com.td.wallendar.dtos.request.AddMembersRequest;
import com.td.wallendar.dtos.request.AddPaymentRequest;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.mappers.GroupMapper;
import com.td.wallendar.utils.networking.RetrofitUtils;

import java.util.List;

import io.reactivex.Single;

public class GroupsRepositoryImpl implements GroupsRepository {

    public final GroupsService groupsService;

    public GroupsRepositoryImpl(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @Override
    public Single<List<Group>> getGroupsByUser(final long userId) {
        return RetrofitUtils.performRequest(groupsService.getGroupsByUserId(userId)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> getGroup(final long groupId) {
        return RetrofitUtils.performRequest(groupsService.getGroupById(groupId)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> createGroup(AddGroupRequest addGroupRequest) {
        return RetrofitUtils.performRequest(groupsService.createGroup(addGroupRequest)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> addMemberByEmail(long groupId, AddMemberByEmailRequest addMemberByEmailRequest) {
        return RetrofitUtils.performRequest(groupsService.addMemberByEmail(groupId, addMemberByEmailRequest)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> addMembers(final long groupId, AddMembersRequest addMembersRequest) {
        return RetrofitUtils.performRequest(groupsService.addMembers(groupId, addMembersRequest)).map(GroupMapper::toModel);
    }

    @Override
    public Single<Group> addPayment(final long groupId, AddPaymentRequest addPaymentRequest) {
        return RetrofitUtils.performRequest(groupsService.addPayment(groupId, addPaymentRequest)).map(GroupMapper::toModel);
    }
}
