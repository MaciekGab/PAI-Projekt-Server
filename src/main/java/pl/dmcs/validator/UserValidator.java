package pl.dmcs.validator;

import org.elasticsearch.common.Strings;
import pl.dmcs.helper.XmlHelper;
import pl.dmcs.model.User;
import pl.dmcs.model.Users;
import pl.dmcs.service.UserService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Optional;

public class UserValidator {

    private final UserService userService = new UserService();

    public String validateLogin(User user) throws JAXBException, FileNotFoundException {
        if (Strings.isNullOrEmpty(user.getLogin()) || Strings.isNullOrEmpty(user.getPassword())) {
            return "Nie podano loginu lub/i hasła.";
        }

        Users users = userService.getAllUsers();

        Optional<User> userOpt = users.findByLogin(user.getLogin());

        if (userOpt.isPresent()) {
            if (!userOpt.get().getPassword().equals(user.getPassword())) {
                return "Nieprawidłowe hasło.";
            }
        } else {
            return "Nieznaleziono użytkownika.";
        }

        return null;
    }

    public String validateRegistration(User user) throws JAXBException, FileNotFoundException {
        if (Strings.isNullOrEmpty(user.getLogin()) || Strings.isNullOrEmpty(user.getPassword())) {
            return "Nie podano loginu lub/i hasła.";
        }

        Optional<User> userOpt = userService.getUserByLogin(user.getLogin());

        if (userOpt.isPresent()) {
            return "Użytkownik o takim loginie już istnieje.";
        }

        return null;
    }

    public String validatePasswordRemind(User user) throws JAXBException, FileNotFoundException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            return "Nie podano loginu.";
        }

        Optional<User> userOpt = userService.getUserByLogin(user.getLogin());

        if (!userOpt.isPresent()) {
            return "Użytkownik o takim loginie nie istnieje.";
        }

        return null;
    }



}
