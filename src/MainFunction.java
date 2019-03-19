import java.util.Scanner;

public class MainFunction {
    
    public static void main(String[] args) {
        String data;
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            data = scanner.nextLine();
        } else {
            data = "";
        }
        scanner.close();
        InputReader inputReader = new InputReader(data);
        if (!inputReader.isValid(inputReader.getData())) {
            System.out.print("WRONG FORMAT!");
            return;
        }
        Expression poly = new Expression(inputReader.getProcessed());
        try {
            String output = poly.derivative().replaceFirst("^\\+", "");
            Expression op = new Expression(output);
            System.out.println(op.getData());
        } catch (Exception e) {
            System.out.println("WRONG FORMAT!");
        }
    }
}
