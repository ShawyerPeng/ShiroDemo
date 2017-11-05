package service;

import model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    /**
     * 创建用户
     */
    Integer insertUser(User user);

    /**
     * 更新用户
     */
    Integer updateUser(User user);

    /**
     * 删除用户
     */
    Integer deleteUser(Integer userId);

    /**
     * 修改密码
     */
    Integer changePassword(Integer userId, String newPassword);

    /**
     * 根据 ID 得到对象
     */
    User selectOne(Integer userId);

    /**
     * 返回所有用户列表
     */
    List<User> selectAll();

    /**
     * 根据用户名查找用户
     */
    User selectByUsername(String username);

    /**
     * 根据用户名查找其角色
     */
    //Set<String> selectRoles(String username);

    /**
     * 根据用户名查找其权限
     */
    //Set<String> selectPermissions(String username);

    /**
     * 用户名是否存在
     */
    boolean usernameIsExist(String username);

    /**
     * 登录验证
     */
    User login(String username, String password);

}
