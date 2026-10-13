package in.SpringBoot;

import in.SpringBoot.aiTools.CalculatorTool;
import in.SpringBoot.aiTools.CurrencyExchangeTool;
import in.SpringBoot.aiTools.WeatherTool;
import in.SpringBoot.aiTools.WebsiteTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteBuilderService {

    public static ChatClient chatClient;

    private static WebsiteTools websiteTools;




    private static List<Message> history = new ArrayList<>();

    public WebsiteBuilderService(ChatClient.Builder builder,
                                 WebsiteTools websiteTools) {
        this.chatClient = builder.build();
        this.websiteTools = websiteTools;


    }

    private static final String SYSTEM_PROMPT = """
            You are a helper AI assistant with access to external tools.
            
            Follow these rules:
                        1. Create a separate directory for every website.
                        2. Create index.html.
                        3. Create style.css.
                        4. Create script.js when JavaScript is useful.
                        5. Build modern, beautiful and responsive websites.
                        6. Use only HTML, CSS and vanilla JavaScript.
                        7. Do not just return website code in your response. Actually create the files using tools.
                        8. After creating the website, list the project files.
                        9. Read important files again if needed and fix obvious problems.
                        10. Finish only when the complete website has been created.
            
            """;


    public static String generate(String message) {

        history.add(new UserMessage(message));

        String output = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages( history)
                .tools(websiteTools)
                .call()
                .content();

        // ASSISTANT role
        history.add(new AssistantMessage(output));

        return output;
    }

}
