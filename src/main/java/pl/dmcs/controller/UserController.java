package pl.dmcs.controller;

import pl.dmcs.enums.ResponseStatus;
import pl.dmcs.model.User;
import pl.dmcs.request.UserRequest;
import pl.dmcs.response.UserResponse;
import pl.dmcs.service.UserService;
import pl.dmcs.validator.UserValidator;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class UserController {

    private final UserValidator userValidator = new UserValidator();
    private final UserService userService = new UserService();

    public UserResponse handleUserRequest(UserRequest userRequest) throws JAXBException, FileNotFoundException {
        if (userRequest.isLogin()) {
            return login(userRequest);
        } else if (userRequest.isRegistration()) {
            return registration(userRequest);
        } else {
            return passwordRemind(userRequest);
        }
    }

    private UserResponse registration(UserRequest userRequest) throws JAXBException, FileNotFoundException {
        User user = userRequest.getUser();

        String errorMessage = userValidator.validateRegistration(user);

        if (errorMessage != null) {
            return new UserResponse(ResponseStatus.ERROR, errorMessage, user);
        }

        userService.addUser(user);

        return new UserResponse(ResponseStatus.OK, null, user);
    }

    private UserResponse login(UserRequest userRequest) throws JAXBException, FileNotFoundException {
        User user = userRequest.getUser();

        String errorMessage = userValidator.validateLogin(user);

        if (errorMessage != null) {
            return new UserResponse(ResponseStatus.ERROR, errorMessage, user);
        }

        return new UserResponse(ResponseStatus.OK, null, user);
    }

    private UserResponse passwordRemind(UserRequest userRequest) throws JAXBException, FileNotFoundException {
        User user = userRequest.getUser();

        String errorMessage = userValidator.validatePasswordRemind(user);

        if (errorMessage != null) {
            return new UserResponse(ResponseStatus.ERROR, errorMessage, user);
        }

        User userByLogin = userService.getUserByLogin(user.getLogin()).get();

        return new UserResponse(ResponseStatus.OK, null, userByLogin);
    }
}
