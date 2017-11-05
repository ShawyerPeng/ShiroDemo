package service;

import model.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> selectByUserId(Integer userId);

    Integer insert(UserRole record);

    Integer deleteById(Integer id);
}
