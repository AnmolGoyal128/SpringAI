package in.SpringBoot.aiTools;

public class CalculatorTool {

    public double calculate(double a, double b, String operation) {
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
