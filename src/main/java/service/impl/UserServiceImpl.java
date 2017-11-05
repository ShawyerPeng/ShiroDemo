package service.impl;

import mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;
import service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Integer changePassword(Integer userId, String newPassword) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User selectOne(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    //@Override
    //public Set<String> selectRoles(String username) {
    //    User user = selectByUsername(username);
    //    if (user == null) {
    //        return Collections.emptySet();
    //    }
    //    return roleService.selectRoles(user.getRoleIds().toArray(new Long[0]));
    //}
    //
    //@Override
    //public Set<String> selectPermissions(String username) {
    //    return null;
    //}

    @Override
    public boolean usernameIsExist(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public User login(String username, String password) {
        return userMapper.checkLogin(username, password);
    }
}
