package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.models.Group;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;

public interface GroupsRepository {
    Single<List<Group>> getGroupsByUser(final Long userId);

    Single<Group> getGroup(final Long groupId);

    Single<Group> createGroup(final String groupTitle, final Long ownerId);

    // Members email
    Single<String> addMembers(final Set<String> members);

    Single<String> addCharge(final Long groupId,
                             final Long ownerId,
                             final String description,
                             final double amount
    );

    Single<String> addPayment(final Long groupId,
                              final Long ownerId,
                              final double amount
    );
}
