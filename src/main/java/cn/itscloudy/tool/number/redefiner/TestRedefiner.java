package cn.itscloudy.tool.number.redefiner;

public class TestRedefiner extends IntegerRedefiner {

    @Override
    protected char[] chars() {
        return new char[]{'@', '#', '$'};
    }

    public static void main(String[] args) {
        TestRedefiner t = new TestRedefiner();
        System.out.println(t.add("#@@", "#@#@$#"));
    }
}
