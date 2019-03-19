class InputReader {
    private String data;
    
    InputReader(String input) {
        this.data = input;
    }
    
    String getData() {
        return data;
    }
    
    Boolean isValid(String input) {
        if (input.length() == 0) {
            return false;
        }
        if (input.matches(".*[^sinco()^x\\d \t+*\\-].*") ||
                input.matches(".*\\^[ \t]*[+-][ \t]+[\\d].*") ||
                input.matches(".*[+-][ \t]*[+-][ \t]*[+-][^\\d].*")) {
            return false;
        }
        if (input.matches(".*s[ \t]+i[ \t]*n.*") ||
                input.matches(".*s[ \t]*i[ \t]+n.*") ||
                input.matches(".*c[ \t]+o[ \t]*s.*") ||
                input.matches(".*c[ \t]*o[ \t]+s.*")) {
            return false;
        }
        String nonSpace;
        nonSpace = input.replaceAll("[ \t]", "");
        return (!nonSpace.matches(".*\\^[+-]{2,}.*") &&
                !nonSpace.matches(".*[+-]{4,}.*"));
    }
    
    String getProcessed() {
        data = ("+" + data).replace("++", "+");
        data = data.replace("--", "+");
        data = data.replace("-+", "-").replace("+-", "-");
        data = data.replace("--", "+").replace("++", "+");
        return data;
    }
    
}

