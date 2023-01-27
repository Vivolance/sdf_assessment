package Calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double num1;
        double num2;
        double result;
        double last = 0;

        //While loop to continuously prompt user to input until user decides to 'exit'
        while (true) {
            System.out.print("Enter any equation in the following format: num1<space>'+-/*'<space>num2 " +
                    "(e.g. 2 + 3) or 'exit' to quit. Type '$last' in num1 or num2 to use last result: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = input.split(" ");

            if (parts[0].equals("$last")) {
                num1 = last;
            } else {
                num1 = Double.parseDouble(parts[0]);
            }

            if (parts[2].equals("$last")) {
                num2 = last;
            } else {
                num2 = Double.parseDouble(parts[2]);
            }

            char operator = parts[1].charAt(0);

            //Different cases of operator functions
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                default:
                    System.out.println("Invalid Operator, please enter only +,-,*,/");
                    continue;
            }

            last = result;
            //Printing out the result
            System.out.println("Result: " + result);
        }

        System.out.println("You have exited the program, see you again!");

    }
}