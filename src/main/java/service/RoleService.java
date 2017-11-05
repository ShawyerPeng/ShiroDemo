package service;

import model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    /**
     * 创建角色
     */
    public Integer insertRole(Role role);

    /**
     * 更新角色
     */
    public Integer updateRole(Role role);

    /**
     * 删除角色
     */
    public Integer deleteRole(Integer roleId);

    /**
     * 查找单一角色
     */
    public Role selectOne(Integer roleId);

    /**
     * 查找全部角色
     */
    public List<Role> selectAll();

    /**
     * 根据角色编号得到角色标识符列表
     */
    public Set<String> selectRoles(Integer... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     */
    public Set<String> selectPermissions(Integer[] roleIds);
}
