public class App {
    public static void main(String[] args) {
        ICandyEater eater;
        CandyServiceBase candyServiceBase = new CandyServiceBase(null) {
            @Override
            public void addCandy(ICandy candy) {
                System.out.println("eat");
            }
        };
    }
}
