package pl.dmcs.server;

import pl.dmcs.controller.UserController;
import pl.dmcs.enums.RequestType;
import pl.dmcs.enums.ResponseStatus;
import pl.dmcs.request.BookRequest;
import pl.dmcs.request.Request;
import pl.dmcs.request.UserRequest;
import pl.dmcs.response.BookResponse;
import pl.dmcs.service.BookSearchService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EchoClientHandler extends Thread {

    private final UserController userController = new UserController();
    private final BookSearchService bookSearchService = new BookSearchService();
    private final Socket clientSocket;

    public EchoClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                Request request = (Request) in.readObject();

                if (request.getRequestType().equals(RequestType.QUIT)) {
                    break;
                }
                else if (request.getRequestType().equals(RequestType.GET_BOOKS)) {
                    BookRequest bookRequest = (BookRequest) request;
                    out.writeObject(bookSearchService.searchedBooks(bookRequest));

                }
                else {
                    UserRequest userRequest = (UserRequest) request;
                    out.writeObject(userController.handleUserRequest(userRequest));
                }
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException | JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}
