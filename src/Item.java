import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item implements Term {
    private String data;
    
    Item(String input) {
        setData(input);
    }
    
    private void setData(String string) {
        data = string;
    }
    
    private String singleDerivative(String up) throws Exception {
        String unprocessed = up.replace("%", "*")
                .replace("{", "(").replace("}", ")");
        if (unprocessed.matches("[+-]?\\d+")) {
            return new Num(unprocessed).derivative();
        } else if (unprocessed.matches("x(?:\\^\\d*)?")) {
            return new XPower(unprocessed).derivative();
        } else if (unprocessed.matches("sin[(].*[)](?:\\^\\d*)?")) {
            return new SinxPower(unprocessed).derivative();
        } else if (unprocessed.matches("cos[(].*[)](?:\\^\\d*)?")) {
            return new CosxPower(unprocessed).derivative();
        } else if (unprocessed.matches("^[(].*[)]$")) {
            return new Expression(unprocessed).derivative();
        } else {
            throw new Exception();
        }
    }
    
    @Override
    public String derivative() throws Exception {
        String unprocessed = getData();
        String pre = "[(][^()]*[)]";
        Pattern prePattern = Pattern.compile(pre);
        Matcher preM = prePattern.matcher(unprocessed);
        while (preM.find()) {
            unprocessed = unprocessed.replace(
                    preM.group(), preM.group().replace("*", "%")
                            .replace("(", "{").replace(")", "}"));
            preM = prePattern.matcher(unprocessed);
        }
        String regex = ".*?\\*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unprocessed);
        if (matcher.find()) {
            String little = matcher.group();
            String big = unprocessed.replaceFirst(".*?\\*", "");
            little = little.replace("%", "*")
                    .replace("{", "(").replace("}", ")");
            big = big.replace("%", "*")
                    .replace("{", "(").replace("}", ")");
            little = little.replaceAll("\\*$", "");
            String littleDer;
            String bigDer = new Item(big).derivative();
            if (little.matches("[+-]?\\d+")) {
                if (bigDer.matches("0")) {
                    return "0";
                }
                return little + "*" + bigDer;
            } else if (little.matches("x(?:\\^\\d*)?")) {
                littleDer = new XPower(little).derivative();
            } else if (little.matches("sin[(].*[)](?:\\^\\d*)?")) {
                littleDer = new SinxPower(little).derivative();
            } else if (little.matches("cos[(].*[)](?:\\^\\d*)?")) {
                littleDer = new CosxPower(little).derivative();
            } else if (little.matches("[(].*[)]")) {
                littleDer = new Expression(little).derivative();
            } else {
                throw new Exception();
            }
            String ans = "(" + littleDer + ")*" + big + "+(";
            if (bigDer.matches("0")) {
                if (littleDer.matches("0")) {
                    return "0";
                }
                return big + "*" + littleDer;
            }
            return ans + bigDer + ")*" + little;
        }
        return singleDerivative(unprocessed);
    }
    
    @Override
    public void optimize() {
    
    }
    
    @Override
    public String getData() {
        optimize();
        return data;
    }
}
