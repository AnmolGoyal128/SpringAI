package in.SpringAi.ChatBot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatBotService {
    private ChatClient chatClient;

    public ChatBotService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    private List<Message> history = new ArrayList<>();



    public String chat(String message) {

        String prompt = """
                You are a customer support executive of
                our food delivery application called Tomato.
                Response to customer query professionally.
                Always respond in 2 line.
                
                if user is furious, or angry or have any issue talk to him quitly and pleasently so on
                Do not answer to any other query other then food delivery""" + message;

        history.add(new UserMessage(prompt));
        String output = chatClient.prompt()
                .messages(history)
                .call()
                .content();
        history.add(new AssistantMessage(output));
        return output;
    }
}
