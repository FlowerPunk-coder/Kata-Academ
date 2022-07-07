import java.util.Scanner;

public class Calculator {

    private static final String[] ROMAN_DIGITS = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private static final int[] ARABIC_DIGITS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private static final String[] ROMAN = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    private static boolean romanFirst;
    private static int positionFirst;
    private static boolean romanSecond;
    private static int positionSecond;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                    Добро пожаловать в программу "Калькулятор".
                    Программа делает расчеты с арабскими и римскими цифрами.
                    Для расчета введите пример в формате: а 'знак' b,
                    где 'знак': +, -, *, /
                        диапазон цифр:
                         от 1 до 10 в арабских цифрах
                         от I до X в римских цифрах
                    или введите слово exit для завершения работы программы.""");
        while (true) {
            System.out.print("Введите пример для расчета или exit для завершения работы программы: ");
            String input = scanner.nextLine();
            if (input.equals("exit".toLowerCase())) {
                break;
            }
            System.out.println(calc(input));
        }
    }

    public static String calc(String input) {
        String[] arr = input.split(" ");
        if (arr.length != 3) {
            throw new IllegalArgumentException("Введенный пример не соответсвует необходимым параметрам!!");
        }
        isRoman(arr[0], arr[2]);
        if (romanFirst && romanSecond) {
            return romanCalc(arr);
        } else if (!romanFirst && !romanSecond) {
            return arabicCalc(arr);
        } else {
            throw new IllegalArgumentException("Можно использовать только одну форму счисления!! Введенное значение: " + input);
        }
    }

    public static void isRoman(String digitOne, String digitTwo) {
        romanFirst = false;
        romanSecond = false;
        positionFirst = -1;
        positionSecond = -1;
        for (int i = 0; i < ROMAN_DIGITS.length; i++) {
            if (digitOne.equals(ROMAN_DIGITS[i])) {
                romanFirst = true;
                positionFirst = i;
            }
            if (digitTwo.equals(ROMAN_DIGITS[i])) {
                romanSecond = true;
                positionSecond = i;
            }
        }
    }

    public static String arabicCalc(String[] arr) {
        int a = Integer.parseInt(arr[0]);
        int b = Integer.parseInt(arr[2]);
        int result = getResult(arr[1], a, b);
        return String.format("%s %s %s = %s", arr[0], arr[1], arr[2], result);
    }

    public static String romanCalc(String[] arr) {
        int a = ARABIC_DIGITS[positionFirst];
        int b = ARABIC_DIGITS[positionSecond];
        int result;
        String romanResult;
        result = getResult(arr[1], a, b);
        if (result < 1) {
            throw new IllegalArgumentException("В римской системе счисления нет нуля и отрицательных чисел!!");
        }
        if (result <= 10) {

            romanResult = ROMAN_DIGITS[result - 1];

        } else if (result == 100) {

            romanResult = "C";

        } else {
            int tens = result / 10;
            int units = result % 10;
            romanResult = ROMAN[tens - 1];
            if (units != 0) {
                romanResult += ROMAN_DIGITS[units - 1];
            }
        }

        return String.format("%s %s %s = %s", arr[0], arr[1], arr[2], romanResult);
    }

    private static int getResult(String sing, int a, int b) {
        int result;
        switch (sing) {
            case ("+") -> result = a + b;
            case ("-") -> result = a - b;
            case ("*") -> result = a * b;
            case ("/") -> result = a / b;
            default -> result = 0;
        }
        return result;
    }

}
