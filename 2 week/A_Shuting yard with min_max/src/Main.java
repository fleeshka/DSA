//Ulyana Chaikouskaya


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Stack<String> stack = new ArrayStack<>();

        Stack<String> stackInt = new ArrayStack<>();


        Scanner scan = new Scanner(System.in);

        String inputExpression = scan.nextLine();

        String[] mathematicalExpression = inputExpression.split(" ");

        String postfixNotation = convertToPostfixNotation(mathematicalExpression, stack);


        System.out.println(resultOfCalculation(postfixNotation, stackInt));



    }

    private static int resultOfCalculation(String mathematicalExpressionPostfixNotation, Stack stack){

        String[] arrayToken = mathematicalExpressionPostfixNotation.split(" ");

        for (int i = 0; i < arrayToken.length; i++){
            if (arrayToken[i].equals("+")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                stack.push(a + b);

            }else if (arrayToken[i].equals("-")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                stack.push(b - a);
            }
            else if (arrayToken[i].equals("/")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                stack.push(b / a);

            }
            else if (arrayToken[i].equals("*")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                stack.push(b * a);

            }
            else if (arrayToken[i].equals("max")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                if (a > b){
                    stack.push(a);
                }else {
                    stack.push(b);
                }
            }else if (arrayToken[i].equals("min")){
                int a = (int) stack.pop();
                int b = (int) stack.pop();
                if (a > b){
                    stack.push(b);
                }else {
                    stack.push(a);
                }
            }else if(((Integer.parseInt(arrayToken[i])) >= 0) || (Integer.parseInt(arrayToken[i])) <= 9){
                int k = Integer.parseInt(arrayToken[i]);
                stack.push(k);
            }
        }
        return (int) stack.pop();



    }


    private static String convertToPostfixNotation(String[] mathematicalExpression, Stack stack) {

        String postfixNotation = "";

        for (int i = 0; i < mathematicalExpression.length; i++) {
            String currentElement = mathematicalExpression[i];
            if (currentElement.equals("*") || currentElement.equals("/")) {
                while (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/"))) {
                    postfixNotation += stack.pop() + " ";
                }
                stack.push(currentElement);

            } else if (currentElement.equals("(")) {
                stack.push(currentElement);

            } else if (currentElement.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfixNotation += stack.pop() + " ";
                }
                if (!stack.isEmpty() && stack.peek().equals("(")){
                stack.pop();
                }

                if (!stack.isEmpty() && (stack.peek().equals("max") || stack.peek().equals("min"))) {
                    postfixNotation += stack.pop() + " ";
                }

            } else if (currentElement.equals("+") || currentElement.equals("-")) {
                while (!stack.isEmpty() && (stack.peek().equals("+") || stack.peek().equals("-")
                        || stack.peek().equals("*") || stack.peek().equals("/")
                        || stack.peek().equals("max") || stack.peek().equals("min"))) {
                    postfixNotation += stack.pop() + " ";
                }
                stack.push(currentElement);

            } else if (currentElement.equals("min") || currentElement.equals("max")) {
                stack.push(currentElement);

            } else if (currentElement.equals(",")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfixNotation += stack.pop() + " ";
                }



            } else {
                postfixNotation += currentElement + " ";
            }
        }

        while (!stack.isEmpty()) {
            postfixNotation += stack.pop() + " ";
        }

        return postfixNotation.trim();
    }





}
interface Stack<T> {
    void push(T item);
    T pop();
    T peek();
    int size();
    boolean isEmpty();
}

class ArrayStack<T> implements Stack<T> {
    T[] items;
    int stackSize;


    public ArrayStack() {
        this.stackSize = 0;
        items = (T[]) new Object[123];
    }

    @Override
    public int size() {
        return this.stackSize;
    }

    @Override
    public boolean isEmpty() {
        return (this.stackSize == 0);
    }

    @Override
    public void push(T item) {
        this.items[stackSize] = item;
        //this.items.add(this.stackSize, item);
        this.stackSize++;
    }

    @Override
    public T pop() {
        if (this.stackSize < 0) {
            throw new RuntimeException("Cannot pop() from an empty stack");
        }
        this.stackSize--;

        T item = this.items[stackSize];
        this.items[stackSize] = null;

        /*T item = this.items.get(this.stackSize);
        this.items.remove(this.stackSize);*/

        return item;
    }

    @Override
    public T peek() {
        if (this.stackSize <= 0){
            return (T) "gh";
        }
        return this.items[stackSize - 1];
    }
}
