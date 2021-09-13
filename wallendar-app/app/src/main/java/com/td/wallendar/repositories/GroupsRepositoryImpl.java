package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.repositories.mappers.GroupsMapper;
import com.td.wallendar.service.GroupsService;

import java.util.List;

import io.reactivex.Observable;

public class GroupsRepositoryImpl implements GroupsRepository {

    public GroupsService groupsService;

    public GroupsRepositoryImpl() {
        this.groupsService = new ServiceModule().init().create(GroupsService.class);
    }

    @Override
    public Observable<List<Group>> getGroupsByUser(final Long userId) {
        return groupsService.getGroupsByUserId(userId.toString()).map(GroupsMapper::toModel);
    }

    @Override
    public Group getGroup(final Long groupId) {
        return new Group();
    }
}
