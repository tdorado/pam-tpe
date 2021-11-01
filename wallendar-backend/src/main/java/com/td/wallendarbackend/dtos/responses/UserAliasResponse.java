package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.UserAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAliasResponse {
    private long id;
    private String alias;

    public UserAliasResponse(UserAlias userAlias) {
        this.id = userAlias.getId();
        this.alias = userAlias.getAlias();
    }
}
