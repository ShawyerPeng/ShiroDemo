package service.impl;

import mapper.RoleMapper;
import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Integer insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public Integer updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public Integer deleteRole(Integer roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public Role selectOne(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Role> selectAll() {
        return null;
    }

    @Override
    public Set<String> selectRoles(Integer... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(Integer roleId : roleIds) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if(role != null) {
                roles.add(role.getRoleName());
            }
        }
        return roles;
    }

    @Override
    public Set<String> selectPermissions(Integer[] roleIds) {
        return null;
    }
}
