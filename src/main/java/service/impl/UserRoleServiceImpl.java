package service.impl;

import mapper.UserRoleMapper;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> selectByUserId(Integer userId) {
        return userRoleMapper.selectByUserId(userId);
    }

    @Override
    public Integer insert(UserRole record) {
        return userRoleMapper.insert(record);
    }

    @Override
    public Integer deleteById(Integer id) {
        return userRoleMapper.deleteByPrimaryKey(id);
    }
}
