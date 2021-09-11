package com.td.wallendar.db.repositories;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;
import com.td.wallendar.repositories.GroupsRepository;

import java.util.ArrayList;
import java.util.List;

public class GroupsRepositoryImpl implements GroupsRepository {
    @Override
    public List<Group> getGroupsByUser(final Long userId) {
        return new ArrayList<>();
    }

    @Override
    public Group getGroup(final Long groupId) {
        return new Group();
    }
}
