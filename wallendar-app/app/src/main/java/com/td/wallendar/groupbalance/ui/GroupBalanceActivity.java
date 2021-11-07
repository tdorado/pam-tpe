package com.td.wallendar.groupbalance.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.groupbalance.GroupBalanceAdapter;
import com.td.wallendar.groupbalance.OnGroupRemindClickedListener;
import com.td.wallendar.groupbalance.OnGroupSettleUpClickedListener;
import com.td.wallendar.models.Debt;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.text.DecimalFormat;
import java.util.List;

public class GroupBalanceActivity extends AbstractActivity implements GroupBalanceView,
        OnGroupSettleUpClickedListener, OnGroupRemindClickedListener {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final String GROUP_ID = "GROUP_ID";

    private GroupBalancePresenter groupBalancePresenter;
    private GroupBalanceAdapter groupBalanceAdapter;

    private long groupId;
    private boolean needsToRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_balance);
        groupId = getIntent().getExtras().getLong(GROUP_ID);

        setUpView();
        createPresenter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (needsToRefresh) {
            final Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        groupBalancePresenter.onViewAttached(groupId);
    }

    @Override
    public void onStop() {
        super.onStop();
        groupBalancePresenter.onViewDetached();
    }

    @Override
    public void onGroupSettleUpClick(Debt debt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.settle_up_debt));
        builder.setMessage(getString(R.string.are_you_sure));
        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            groupBalancePresenter.onSettleUpDebtClicked(debt);
            dialog.dismiss();
        });
        builder.setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onGroupRemindClick(Debt debt) {
        Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW);
        whatsAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String phoneNumber = debt.getTo().getPhoneNumber();
        String messageText = getString(R.string.pay_me_what_you_owe_me, df.format(debt.getAmount()));
        String deeplink = "http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + messageText;
        whatsAppIntent.setPackage("com.whatsapp");
        whatsAppIntent.setData(Uri.parse(deeplink));
        try{
            startActivity(whatsAppIntent);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(), getString(R.string.whatsapp_not_installed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void bind(List<Debt> debts) {
        groupBalanceAdapter.setDataset(debts);
    }

    @Override
    public void failedToLoadDebts() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.failed_to_load_debts), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedToSettleUpDebt() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.failed_to_settle_up), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSettleUpPaymentDone(Debt debt) {
        if (groupBalanceAdapter != null) {
            needsToRefresh = true;
            groupBalanceAdapter.removeFromDataset(debt);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return groupBalancePresenter;
    }

    private void setUpView() {
        groupBalanceAdapter = new GroupBalanceAdapter(getLoggedUserId());
        groupBalanceAdapter.setOnGroupSettleUpClickedListener(this);
        groupBalanceAdapter.setOnGroupRemindClickedListener(this);
        RecyclerView recycler = findViewById(R.id.group_balance_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupBalanceAdapter);

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.balances);
    }

    private void createPresenter() {
        groupBalancePresenter = (GroupBalancePresenter) getLastNonConfigurationInstance();

        if (groupBalancePresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            groupBalancePresenter = new GroupBalancePresenter(this, groupsRepository, schedulerProvider);
        }
    }
}
