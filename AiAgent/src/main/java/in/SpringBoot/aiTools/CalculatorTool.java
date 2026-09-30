package in.SpringBoot.aiTools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTool {


    @Tool(description = """
            Performs Arithmetic operations , calculations,
            Supported operations: add, subtract, multiply, divide, mod, power
            """)
    public double calculate(
            @ToolParam(description = "First number")
            double a,
            @ToolParam(description = "Second number")
            double b,
            @ToolParam(description = "Operation: add, subtract, multiply, divide, mod, power")
            String operation) {

        System.out.println("Calculator Too called");
        if (operation.equals("add")) {
            return a + b;
        }
        else if (operation.equals("subtract")) {
            return a - b;
        }
        else if (operation.equals("multiply")) {
            return a * b;
        }
        else if (operation.equals("divide")) {
            if (b == 0){
                throw new IllegalArgumentException("Divide by zero");
            }
            return a / b;
        } else if (operation.equals("mod")) {
            if (b == 0){
                throw new IllegalArgumentException("Cannot Calculate mode");
            }
            return a % b;

        } else if (operation.equals("power")) {
            return Math.pow(a, b);
        }
        else {
            throw new IllegalArgumentException("Unknown operation " + operation);
        }

    }

}
