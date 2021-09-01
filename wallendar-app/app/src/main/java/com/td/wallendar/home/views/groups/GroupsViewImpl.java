package com.td.wallendar.home.views.groups;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.home.views.models.Group;

import java.util.List;

public class GroupsViewImpl extends RecyclerView implements GroupsView {
    public GroupsViewImpl(@NonNull Context context) {
        this(context, null);
    }

    public GroupsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(final GroupsAdapter groupsAdapter) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(groupsAdapter);
    }

    @Override
    public void listGroups(List<Group> groups) {

    }

    @Override
    public void enterGroup(Group group) {

    }
}
