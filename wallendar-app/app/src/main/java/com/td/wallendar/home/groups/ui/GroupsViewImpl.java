package com.td.wallendar.home.groups.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.db.repositories.GroupsRepositoryImpl;
import com.td.wallendar.home.groups.GroupsAdapter;
import com.td.wallendar.models.Group;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupsViewImpl extends RecyclerView implements GroupsView, View.OnClickListener {

    private GroupsPresenter groupsPresenter;
    private GroupsAdapter groupsAdapter;

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
    public void bind() {
        this.groupsAdapter = new GroupsAdapter(this);
        setHasFixedSize(true);

        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        setAdapter(groupsAdapter);

        buildPresenter();

        groupsPresenter.listGroups(getUserId());
    }

    @Override
    public void listGroups(List<Group> groups) {
        groupsAdapter.setData(groups);
    }

    @Override
    public void enterGroup(Group group) {
        // TODO enter new activity
    }

    @Override
    public void buildPresenter() {
        if (groupsPresenter != null) {
            groupsPresenter = new GroupsPresenter(new WeakReference<>(this), new GroupsRepositoryImpl());
        }
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public void onClick(View view) {
        final int itemPosition = this.getChildLayoutPosition(view);
        final Long groupId = groupsAdapter.getGroupIdAt(itemPosition);
        groupsPresenter.getGroup(groupId);
    }
}
