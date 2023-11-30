package com.are_commerce.ecommercewebsite.Service;

//import com.are_commerce.ecommercewebsite.Security.SecurityConfig;
import com.are_commerce.ecommercewebsite.Exceptions.UserExistsException;
import com.are_commerce.ecommercewebsite.Model.DBUser;
import com.are_commerce.ecommercewebsite.Model.Role;
import com.are_commerce.ecommercewebsite.Model.UserDTO;
import com.are_commerce.ecommercewebsite.Repository.DBUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
//import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final DBUserRepository userRepository;

    @Autowired
    public RegistrationService(DBUserRepository dbUserRepository, PasswordEncoder passwordEncoder){
        this.userRepository = dbUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registration(UserDTO userDTO) throws UserExistsException, IllegalArgumentException {
        if (userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getEmail() == null) {
            throw new IllegalArgumentException("Username, password, and email must not be null");
        }
        /*if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (!isStrongPassword(userDTO.getPassword())) {
            throw new IllegalArgumentException("Password is not strong enough");
        }*/
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserExistsException("Username is already in use");
        }
        DBUser dbUser = new DBUser(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), Role.USER, userDTO.getEmail()
                , userDTO.getFirstName(), userDTO.getLastName(), userDTO.getAddress(), userDTO.getPhoneNumber());
        try {
            userRepository.save(dbUser);
        } catch (DataAccessException e) {
            //throw new
        }
        return "success";
    }
}
