
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

    private final String System_Prompt = """
            You are a customer support executive for our 
            food delivery app named tomato
            
            Your job is to identify the customer's main
            problem and urgency. Answer then related to their query in 2 line
            
            Use professional language. if user has an issue
            use word like I understand your frustration,
            I am very sorry for your trouble etc
            
            Do not NSWER ANY OTHER QUESTION WHICH IS NOT 
            RELATED TO ORDERING FOOD QUERY, REFUND QUERY,
            ORDER TRACKING STATUS QUERY OR COMPANY POLICY QUERY""";



    public String chat(String message) {


        history.add(new UserMessage(message));
        String output = chatClient.prompt()
                .system(System_Prompt)
                .messages(history)
                .call()
                .content();
        history.add(new AssistantMessage(output));
        return output;
    }
}
