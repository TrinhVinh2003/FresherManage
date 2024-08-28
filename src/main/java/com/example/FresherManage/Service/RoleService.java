package com.example.FresherManage.Service;


import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);

    List<RoleResponse> getAll();

    void deleteRole(String nameRole);
}
