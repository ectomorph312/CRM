package io.fortylines.hrcrm.service.Impl;

import io.fortylines.hrcrm.dto.CreateUserDto;
import io.fortylines.hrcrm.dto.ReadUserDto;
import io.fortylines.hrcrm.dto.UpdateUserDto;
import io.fortylines.hrcrm.entity.User;
import io.fortylines.hrcrm.mapper.UserMapper;
import io.fortylines.hrcrm.repository.UserRepository;
import io.fortylines.hrcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReadUserDto getUserProfile(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User is not found"));

        return userMapper.toReadUserDto(user);
    }

    @Override
    public User getById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new EntityNotFoundException("User with this id: " + id + " not found"));
    }

    @Override
    public ReadUserDto createNewUser(CreateUserDto createUserDto) {
        User user = new User();
        String username = (createUserDto.getFirstName() + createUserDto.getLastName()).toLowerCase();
        String role = createUserDto.getRoles();

        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setPassword(createUserDto.getPassword());
        user.setUsername(username.replaceAll("\\s+",""));
        user.setActive(true);

        User savedUser = userRepository.save(user);
        return userMapper.toReadUserDto(savedUser);
    }

    @Override
    public ReadUserDto update(Long id, UpdateUserDto updateUserDto) {
        User user = getById(id);
        String  deactivate = updateUserDto.getActive();

        if (deactivate.equals("true"))
            user.setActive(false);

        if (deactivate.equals("false"))
            user.setActive(true);

        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setUsername(updateUserDto.getUsername());
        user.setPassword(updateUserDto.getPassword());
//        user.setRoles(updateUserDto.getRoles());

        User updateUser = userRepository.save(user);

        return userMapper.toReadUserDto(updateUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}












































