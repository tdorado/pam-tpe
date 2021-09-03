package com.td.wallendar.home.repositories;

import com.td.wallendar.home.models.Group;
import com.td.wallendar.home.models.User;

import java.util.List;

public interface GroupRepository {
    List<Group> getGroupsByUser(final User user);
}
