package com.td.wallendar.repositories

import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.service.GroupsService
import com.td.wallendar.utils.mappers.GroupMapper
import com.td.wallendar.utils.networking.RetrofitUtils
import io.reactivex.Single

class GroupsRepositoryImpl(val groupsService: GroupsService) : GroupsRepository {
    override fun getGroupsByUser(userId: Long): Single<MutableList<Group>> {
        return RetrofitUtils
                .performRequest(groupsService.getGroupsByUserId(userId))
                .map { GroupMapper.toModel(it) }
    }

    override fun getGroup(groupId: Long): Single<Group> {
        return RetrofitUtils
                .performRequest(groupsService.getGroupById(groupId))
                .map { GroupMapper.toModel(it) }
    }

    override fun createGroup(addGroupRequest: AddGroupRequest): Single<Group> {
        return RetrofitUtils
                .performRequest(groupsService.createGroup(addGroupRequest))
                .map { GroupMapper.toModel(it) }
    }

    override fun addMemberByEmail(groupId: Long, addMemberByEmailRequest: AddMemberByEmailRequest): Single<Group> {
        return RetrofitUtils
                .performRequest(groupsService.addMemberByEmail(groupId, addMemberByEmailRequest))
                .map { GroupMapper.toModel(it) }
    }

    override fun addMembers(groupId: Long, addMembersRequest: AddMembersRequest): Single<Group> =
            RetrofitUtils
                    .performRequest(groupsService.addMembers(groupId, addMembersRequest)).map { GroupMapper.toModel(it) }


    override fun addPayment(groupId: Long, addPaymentRequest: AddPaymentRequest) =
            RetrofitUtils
                    .performRequest(groupsService.addPayment(groupId, addPaymentRequest))
}