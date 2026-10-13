package in.SpringBoot;

import in.SpringBoot.aiTools.CalculatorTool;
import in.SpringBoot.aiTools.CurrencyExchangeTool;
import in.SpringBoot.aiTools.WeatherTool;
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

    private static WeatherTool weatherTool;

    private static CurrencyExchangeTool currencyExchangeTool;




    private static List<Message> history = new ArrayList<>();

    public AgentService(ChatClient.Builder builder,
                        CalculatorTool calculatorTool,
                        WeatherTool weatherTool,
                        CurrencyExchangeTool currencyExchangeTool) {
        this.chatClient = builder.build();
        this.calculatorTool = calculatorTool;
        this.weatherTool = weatherTool;
        this.currencyExchangeTool = currencyExchangeTool;


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
            7. For arithmetic calculations, ALWAYS use the calculator tool.
            8. Always use calculator tool for even trivial calculation
            9. For current weather, ALWAYS use the currentWeather tool.
            10. For currency conversion or exchange rates, ALWAYS use the convertCurrency tool.
            11. You may call multiple tools when solving a multi-step request.
            12. After receiving tool results, explain the answer naturally.
            13. Never invent current weather or exchange-rate information.
            """;


    public static String chat(String message) {

        history.add(new UserMessage(message));

        String output = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages( history)
                .tools(calculatorTool, weatherTool, currencyExchangeTool)
                .call()
                .content();

        // ASSISTANT role
        history.add(new AssistantMessage(output));

        return output;
    }
}
