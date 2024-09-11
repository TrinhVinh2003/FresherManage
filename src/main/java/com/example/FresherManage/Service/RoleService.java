package com.example.FresherManage.Service;

import java.util.List;

import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);

    List<RoleResponse> getAll();

    void deleteRole(String nameRole);
}
