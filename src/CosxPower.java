import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CosxPower extends Factor implements Term {
    CosxPower(String input) {
        super(input);
    }
    
    @Override
    public String derivative() throws Exception {
        String data = getData();
        if (data.matches("1")) {
            return "0";
        } else if (data.matches(".*\\^\\d+")) {
            String ans;
            String regex = "\\d+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            matcher.find();
            BigInteger coefficient = new BigInteger(matcher.group());
            BigInteger power = new BigInteger(matcher.group())
                    .subtract(BigInteger.ONE);
            if (power.subtract(new BigInteger("10000")).signum() == 1) {
                throw new Exception();
            }
            ans = BigInteger.ZERO.subtract(coefficient).toString() + "*sin(";
            String content;
            content = data.replaceFirst("cos[(]", "")
                    .replaceFirst("[)]\\^\\d+$", "");
            ans += content + ")^" + power + "*(";
            ans += new Item(content).derivative();
            ans += ")";
            return ans;
        } else {
            String ans;
            ans = data.replaceFirst("cos", "-sin");
            ans += "*(";
            String content;
            content = data.replaceFirst("cos[(]", "").replaceFirst("[)]$", "");
            ans += new Item(content).derivative();
            ans += ")";
            return ans;
        }
    }
    
    @Override
    public void optimize() {
        if (super.getData().matches(".*\\^0*1")) {
            setData(getData().replaceAll("\\^0*1", ""));
        } else if (super.getData().matches(".*\\^0*")) {
            setData("1");
        }
    }
    
    @Override
    public String getData() {
        optimize();
        return super.getData();
    }
}
