package Service;

import Model.Account;
import Model.Message;
import java.util.List;

import DAO.SocialMediaDAO;

public class SocialMediaService {
    SocialMediaDAO socialMediaDAO;

    public SocialMediaService() {
        socialMediaDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO socialMediaDAO) {
        this.socialMediaDAO = socialMediaDAO;
    }

    public Account registerAccountService(Account account) {
        if((account.getUsername() != "") && (account.getPassword().length() >= 4)) {
            return socialMediaDAO.registerAccount(account);
        }
        return null;
    }

    public Account loginAccountService(String username, String password) {
        return socialMediaDAO.loginAccount(username, password);
    }

    public Message createMessageService(Message message) {
        if(message.getMessage_text() != "") {
            return socialMediaDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessagesService() {
        return socialMediaDAO.getAllMessages();
    }

    public Message getMessageWithIdService(int id) {
        return socialMediaDAO.getMessageWithId(id);
    }

    public Message deleteMessageWithIdService(int id) {
        socialMediaDAO.deleteMessageWithId(id);
        return socialMediaDAO.getMessageWithId(id);
    }

    public Message updateMessageWithIdService(int id, String messageText) {
        if((messageText != "") && (messageText.length() <= 255)) {
            socialMediaDAO.updateMessageWithId(id, messageText);
            return socialMediaDAO.getMessageWithId(id);
        }
        return null;
    }

    public List<Message> getAllMessagesWithIdService(int id) {
        return socialMediaDAO.getAllMessagesWithId(id);
    }
}
