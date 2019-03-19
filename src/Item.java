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
    
    @Override
    public String derivative() throws Exception {
        String unprocessed = getData();
        String pre = "[(][^()]*[)]";
        Pattern prePattern = Pattern.compile(pre);
        Matcher preM = prePattern.matcher(unprocessed);
        while (preM.find()) {
            unprocessed = unprocessed.replace(
                    preM.group(), preM.group().replace("*", "%")
                            .replace("(", "{")
                            .replace(")", "}"));
            preM = prePattern.matcher(unprocessed);
        }
        String regex = ".*?\\*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unprocessed);
        if (matcher.find()) {
            String ans = "(";
            String little = matcher.group();
            String big = unprocessed.replaceFirst(".*?\\*", "");
            little = little.replace("%", "*")
                    .replace("{", "(").replace("}", ")");
            big = big.replace("%", "*")
                    .replace("{", "(").replace("}", ")");
            little = little.replaceAll("\\*$", "");
            if (little.matches("[+-]?\\d*")) {
                ans += new Num(little).derivative();
            } else if (little.matches("x(?:\\^\\d*)?")) {
                ans += new XPower(little).derivative();
            } else if (little.matches("sin[(].*[)](?:\\^\\d*)?")) {
                ans += new SinxPower(little).derivative();
            } else if (little.matches("cos[(].*[)](?:\\^\\d*)?")) {
                ans += new CosxPower(little).derivative();
            } else if (little.matches("[(].*[)]")) {
                ans += new Expression(little).derivative();
            } else {
                throw new Exception();
            }
            ans += ")*" + big + "+(";
            ans += new Item(big).derivative();
            ans += ")*" + little;
            return ans;
        }
        unprocessed = unprocessed.replace("%", "*")
                .replace("{", "(")
                .replace("}", ")");
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
    public void optimize() {
        if (data.matches("$[+-]?0+\\*")) {
            setData("0");
        }
    }
    
    @Override
    public String getData() {
        optimize();
        return data;
    }
}
