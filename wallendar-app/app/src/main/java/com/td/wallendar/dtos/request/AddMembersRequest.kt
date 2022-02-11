package com.td.wallendar.dtos.request

data class AddMembersRequest(val userEmails: MutableSet<String>)