package pl.dmcs.service;

import pl.dmcs.helper.XmlHelper;
import pl.dmcs.model.User;
import pl.dmcs.model.Users;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Optional;

public class UserService {

    private final static String USERS_XML_PATH = "C:\\Users\\mgmac\\IdeaProjects\\server\\users.xml";

    public Users getAllUsers() throws JAXBException, FileNotFoundException {
        return (Users) XmlHelper.fromXml(Users.class, USERS_XML_PATH);
    }

    public Optional<User> getUserByLogin(String login) throws JAXBException, FileNotFoundException {
        Users users = getAllUsers();
        return users.findByLogin(login);
    }

    public void addUser(User user) throws JAXBException, FileNotFoundException {
        Users users = getAllUsers();
        users.addUser(user);

        XmlHelper.toXml(users, USERS_XML_PATH);
    }
}
