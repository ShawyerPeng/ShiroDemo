package service.impl;

import java.util.List;

import mapper.UserExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.UserExtendService;

@Service
public class UserExtendServiceImpl implements UserExtendService {
	@Autowired
	private UserExtendMapper userExtendMapper;
	
	@Override
	public List<String> getRoles(String username) {
		return userExtendMapper.getRoles(username);
	}

    @Override
    public List<String> getPermissions(String username) {
        return userExtendMapper.getPermissions(username);
    }
}
