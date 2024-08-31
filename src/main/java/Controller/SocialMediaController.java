package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.SocialMediaService;
import Model.Account;
import Model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    SocialMediaService socialMediaService;
    public SocialMediaController() {
        socialMediaService = new SocialMediaService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageWithIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = socialMediaService.registerAccountService(account);
        if(registeredAccount==null){
            ctx.status(400);
        }
        else {
            ctx.json(mapper.writeValueAsString(registeredAccount));
            ctx.status(200);
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = socialMediaService.loginAccountService(account.getUsername(), account.getPassword());
        if(loginAccount==null) {
            ctx.status(401);
        }
        else {
            ctx.json(mapper.writeValueAsString(loginAccount));
            ctx.status(200);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = socialMediaService.createMessageService(message);
        if(createdMessage==null){
            ctx.status(400);
        }
        else {
            ctx.json(mapper.writeValueAsString(createdMessage));
            ctx.status(200);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json(socialMediaService.getAllMessagesService());
    }

    private void getMessageWithIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message foundMessage = socialMediaService.getMessageWithIdService(message_id);
        if(foundMessage==null) {
            ctx.status(200);
        }
        else {
            ctx.json(mapper.writeValueAsString(foundMessage));
            ctx.status(200);
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = socialMediaService.deleteMessageWithIdService(message_id);
        if(deletedMessage==null){
            ctx.status(200);
        }
        else {
            ctx.json(mapper.writeValueAsString(deletedMessage));
            ctx.status(200);
        }
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class); 
        Message updatedMessage = socialMediaService.updateMessageWithIdService(message_id, message.getMessage_text());
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }
        else {
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }

    }

    private void getAllMessagesFromUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = socialMediaService.getAllMessagesWithIdService(account_id);
        if(messages == null) {
            ctx.status(200);
        }
        else {
            ctx.json(mapper.writeValueAsString(messages));
            ctx.status(200);
        }
    }
}