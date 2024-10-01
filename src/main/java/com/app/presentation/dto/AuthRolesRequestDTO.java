package com.app.presentation.dto;

import javax.validation.constraints.Size;
import java.util.List;

public record AuthRolesRequestDTO (
        @Size(max = 3, message = "User cannot have more than 3 roles") List<String> rolesListName){
}
