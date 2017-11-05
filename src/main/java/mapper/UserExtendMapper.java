package mapper;

import java.util.List;

import model.User;

public interface UserExtendMapper {
    User selectByUsername(String username);

    List<String> getRoles(String username);

    List<String> getPermissions(String username);
}
