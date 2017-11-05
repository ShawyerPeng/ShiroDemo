package service;

import model.RolePermission;

public interface RolePermissionService {
    RolePermission selectByRoleId(Integer roleId);
}
