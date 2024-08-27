package com.example.test1.Service;


import com.example.test1.Dto.request.RoleRequest;
import com.example.test1.Dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);

    List<RoleResponse> getAll();

    void deleteRole(String nameRole);
}
