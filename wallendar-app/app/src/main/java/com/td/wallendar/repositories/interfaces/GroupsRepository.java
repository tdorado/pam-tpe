package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.dtos.request.AddMemberByEmailRequest;
import com.td.wallendar.dtos.request.AddMembersRequest;
import com.td.wallendar.dtos.request.AddPaymentRequest;
import com.td.wallendar.models.Group;

import java.util.List;

import io.reactivex.Single;

public interface GroupsRepository {
    Single<List<Group>> getGroupsByUser(final long userId);

    Single<Group> getGroup(final long groupId);

    Single<Group> createGroup(AddGroupRequest addGroupRequest);

    Single<Group> addMemberByEmail(final long groupId, AddMemberByEmailRequest addMemberByEmailRequest);

    Single<Group> addMembers(final long groupId, AddMembersRequest addMembersRequest);

    Single<Group> addPayment(final long groupId, AddPaymentRequest addPaymentRequest);
}
