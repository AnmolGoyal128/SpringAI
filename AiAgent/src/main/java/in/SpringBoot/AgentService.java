package in.SpringBoot;

import in.SpringBoot.aiTools.CalculatorTool;
import jdk.security.jarsigner.JarSigner;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.chat.client.ChatClient.builder;

@Service
public class AgentService {

    public static ChatClient chatClient;

    private static CalculatorTool calculatorTool;


    private static List<Message> history = new ArrayList<>();

    public AgentService(ChatClient.Builder builder, CalculatorTool calculatorTool) {
        this.chatClient = builder.build();
        this.calculatorTool = calculatorTool;


    }

    private static final String SYSTEM_PROMPT = """
            You are a helper AI assistant with access to external tools.
            
            Follow these rules:
            1. For arithmetic calculations , ALWAYS use the calculator tool.
            2. After receiving the toll results, explain the answers naturally.
            3. Do NOT use LaTeX.
            4. Do NOT use Markdown.
            5. Do NOT use \\\\[ \\\\] or other mathematical formatting.
            6. Return output in natural language very clearly.
            """;


    public static String chat(String message) {
        history.add(new UserMessage(message));
        String output = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages( history)
                .tools(calculatorTool)
                .call()
                .content();

        // ASSISTANT role
        history.add(new AssistantMessage(output));

        return output;
    }
}
