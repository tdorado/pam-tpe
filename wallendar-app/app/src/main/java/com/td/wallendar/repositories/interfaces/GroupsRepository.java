package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.models.Group;

import java.util.List;

import io.reactivex.Observable;

public interface GroupsRepository {
    Observable<List<Group>> getGroupsByUser(final Long userId);

    Group getGroup(final Long groupId);
}
