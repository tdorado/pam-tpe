package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.models.Group;

import java.util.List;

public interface GroupsRepository {
    List<Group> getGroupsByUser(final Long userId);

    Group getGroup(final Long groupId);
}
