package com.td.wallendar.home.groups.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;
import com.td.wallendar.repositories.GroupsRepository;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupsPresenter {

    private final WeakReference<GroupsView> groupsView;
    private final GroupsRepository groupsRepository;

    public GroupsPresenter(final WeakReference<GroupsView> groupsView,
                           final GroupsRepository groupsRepository) {
        this.groupsView = groupsView;
        this.groupsRepository = groupsRepository;
    }

    void listGroups(final Long userId) {
        final List<Group> groups = groupsRepository.getGroupsByUser(userId);
        groupsView.get().listGroups(groups);
    }

    void getGroup(final Long groupId) {
        final Group group = groupsRepository.getGroup(groupId);
        groupsView.get().enterGroup(group);
    }
}