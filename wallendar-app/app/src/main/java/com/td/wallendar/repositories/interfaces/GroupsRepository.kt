package com.td.wallendar.repositories.interfaces

import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Group
import io.reactivex.Single

interface GroupsRepository {
    open fun getGroupsByUser(userId: Long): Single<MutableList<Group>>
    open fun getGroup(groupId: Long): Single<Group>
    open fun createGroup(addGroupRequest: AddGroupRequest): Single<Group>
    open fun addMemberByEmail(groupId: Long, addMemberByEmailRequest: AddMemberByEmailRequest): Single<Group>
    open fun addMembers(groupId: Long, addMembersRequest: AddMembersRequest): Single<Group>
    open fun addPayment(groupId: Long, addPaymentRequest: AddPaymentRequest): Single<Group>
}