package com.td.wallendar.group.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;

import java.util.List;

public interface GroupView {

    void bindGroup(Group group);

    void bindGroupHistory(List<GroupHistory> historic);

}
