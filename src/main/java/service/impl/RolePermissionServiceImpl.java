package service.impl;

import mapper.RolePermissionMapper;
import model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public RolePermission selectByRoleId(Integer roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }
}
