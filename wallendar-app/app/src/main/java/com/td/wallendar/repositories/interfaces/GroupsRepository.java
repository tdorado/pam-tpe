package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.models.Group;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public interface GroupsRepository {
    Observable<List<Group>> getGroupsByUser(final Long userId);

    Observable<Group> getGroup(final Long groupId);

    Observable<Group> getGroupById(final Long groupId);

    Observable<String> createGroup(final String groupTitle, final Long ownerId);

    // Members email
    Observable<String> addMembers(final Set<String> members);

    Observable<String> addCharge(final Long groupId,
                                 final Long ownerId,
                                 final String description,
                                 final double amount
    );

    Observable<String> addPayment(final Long groupId,
                                  final Long ownerId,
                                  final double amount
    );
}
