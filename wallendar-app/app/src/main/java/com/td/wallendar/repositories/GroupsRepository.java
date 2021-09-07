package com.td.wallendar.repositories;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;

import java.util.List;

public interface GroupsRepository {
    List<Group> getGroupsByUser(final Long userId);

    Group getGroup(final Long groupId);
}
