package service;

import java.util.List;

public interface UserExtendService {
    /**
     * 根据用户名查找对应的所有角色
     */
    List<String> getRoles(String username);

    /**
     * 根据用户名查找对应的所有权限
     */
    List<String> getPermissions(String username);
}
