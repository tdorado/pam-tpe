package com.td.wallendar.repositories.interfaces

import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Group
import io.reactivex.Single

interface GroupsRepository {
    fun getGroupsByUser(userId: Long): Single<MutableList<Group>>
    fun getGroup(groupId: Long): Single<Group>
    fun createGroup(addGroupRequest: AddGroupRequest): Single<Group>
    fun addMemberByEmail(groupId: Long, addMemberByEmailRequest: AddMemberByEmailRequest): Single<Group>
    fun addMembers(groupId: Long, addMembersRequest: AddMembersRequest): Single<Void>
    fun addPayment(groupId: Long, addPaymentRequest: AddPaymentRequest): Single<Void>
}