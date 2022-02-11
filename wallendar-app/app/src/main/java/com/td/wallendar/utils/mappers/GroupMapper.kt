package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.GroupResponse
import com.td.wallendar.models.Group
import java.util.*

object GroupMapper {
    fun toModel(groupsResponses: MutableList<GroupResponse>): MutableList<Group> {
        val groups: MutableList<Group> = ArrayList()
        for (group in groupsResponses) {
            groups.add(toModel(group))
        }
        return groups
    }

    fun toModel(groupResponse: GroupResponse): Group {
        return Group(
                groupResponse.id,
                groupResponse.title,
                ApplicationUserMapper.toModel(groupResponse.owner),
                ApplicationUserMapper.toModel(groupResponse.members),
                ChargeMapper.toModel(groupResponse.charges),
                DebtMapper.toModel(groupResponse.debts),
                PaymentMapper.toModel(groupResponse.payments)
        )
    }
}