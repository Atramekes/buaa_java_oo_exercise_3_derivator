public class MainFunction {
    private static String poly;
    
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        if (!inputReader.isValid()) {
            System.out.print("WRONG FORMAT!");
            return;
        }
        poly = inputReader.getProcessed();
        
        
        System.out.print(poly);
    }
}
