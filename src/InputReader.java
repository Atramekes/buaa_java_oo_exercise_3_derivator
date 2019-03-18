import java.util.Scanner;

class InputReader {
    private String input;
    
    InputReader() {
        String data;
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            data = scanner.nextLine();
        } else {
            data = "";
        }
        scanner.close();
        this.input = data;
    }
    
    Boolean isValid() {
        if (input.length() == 0) {
            return false;
        }
        if (input.matches(".*[^sinco()^x\\d \t+*\\-].*")) {
            return false;
        }
        
        return true;
    }
    
    String getProcessed() {
        input = input.replaceAll("[ \t]", "");
        input = ("+" + input).replace("++", "+");
        input = input.replace("--", "+");
        input = input.replace("-+", "-").replace("+-", "-");
        input = input.replace("--", "+").replace("++", "+");
        return input;
    }
    
}

