package com.td.wallendar.home.groups.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.group.ui.GroupActivity;
import com.td.wallendar.home.groups.GroupsAdapter;
import com.td.wallendar.home.ui.HomeView;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.GroupsRepositoryImpl;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupsViewImpl extends RecyclerView implements GroupsView, View.OnClickListener {

    private GroupsPresenter groupsPresenter;
    private GroupsAdapter groupsAdapter;
    private HomeView homeView;

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
    public void bind(final ExtendedFloatingActionButton addChargeFAB, final HomeView homeView) {
        // Shrink floating button when scrolling, extend at the top. Just fancy fab
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addChargeFAB.extend();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && addChargeFAB.isExtended()) {
                    addChargeFAB.shrink();
                }
            }
        });
        this.homeView = homeView;
        this.groupsAdapter = new GroupsAdapter(this);
        this.groupsPresenter = new GroupsPresenter(new WeakReference<>(this), null);

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
        final Intent intent = new Intent(homeView.getApplicationContext(), GroupActivity.class);
        homeView.startActivity(intent);
    }

    @Override
    public void buildPresenter() {
        if (groupsPresenter != null) {
            groupsPresenter = new GroupsPresenter(new WeakReference<>(this), new GroupsRepositoryImpl());
        }
    }

    @Override
    public Long getUserId() {
        return 1L;
    }

    @Override
    public void onClick(View view) {
        final int itemPosition = this.getChildLayoutPosition(view);
        final Long groupId = groupsAdapter.getGroupIdAt(itemPosition);
        groupsPresenter.getGroup(groupId);
    }
}
