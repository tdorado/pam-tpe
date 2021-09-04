package com.td.wallendar.repositories;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;

import java.util.List;

public interface GroupRepository {
    List<Group> getGroupsByUser(final User user);
}
