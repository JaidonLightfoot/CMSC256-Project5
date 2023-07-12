package cmsc256;
import bridges.connect.Bridges;
import bridges.base.BinTreeElement;


import java.util.Scanner;
import java.util.Stack;

public class Project5 {

    private enum OPERATOR {
        ADD("+"), SUB ("-"), MULT("*"), DIV("/"), MOD("%");
        private final String value;

        OPERATOR(String value){

            this.value = value;
        }
        public String value(){
            return value;
        }
    }

    public static boolean isNumeric(String strNum){ // used to check if the node is a number/leaf
        if(strNum == null){
            return false;
        }

        try {
            double d = Double.parseDouble(strNum);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }


    public static bridges.base.BinTreeElement<String> buildParseTree(String expression){

        BinTreeElement<String> MyTree = new BinTreeElement<>("root", "");
        BinTreeElement<String> current = MyTree; //track current node
        Stack<BinTreeElement> treeElements = new Stack<>(); //stack to back track
        String[] tokens = expression.split(" "); //array to hold each token of the expression

        for(int i = 0; i < tokens.length; i++){ //read each character/token in the expression and perform an action

            if (tokens[i].equals("(")){
                current.setLeft(new BinTreeElement<>()); ;// add node as left child
                treeElements.push(current); //
                current = current.getLeft();

            }
            else if(tokens[i].equals("+") || tokens[i].equals("-")|| tokens[i].equals("*")|| tokens[i].equals("/")||tokens[i].equals("%")) { //this is an operator
                current.setValue(tokens[i]); //set t
                current.setRight(new BinTreeElement<>());//set the root value as the current node
                treeElements.push(current); //add token to the stack
                current = current.getRight();
            }
            else if(tokens[i].equals(")")) {
                if(!treeElements.isEmpty()) {
                    current = treeElements.pop();
                }
            }

            else if(isNumeric(tokens[i])) {  //this should be a number
                current.setValue(tokens[i]);
                current = treeElements.pop();
            }
            else{
                throw new IllegalArgumentException("Expression cannot be parsed. Current token is " + tokens[i]);
            }
        }
        return MyTree;
    }


    public static double evaluate(bridges.base.BinTreeElement<String> tree){
        if(isNumeric(tree.getValue())){ //base case
            return Double.parseDouble(tree.getValue());
        }
        else {
            double left = evaluate(tree.getLeft());
            double right = evaluate(tree.getRight());

            if (tree.getValue().equals("+")) {
                return left + right;
            }
            if (tree.getValue().equals("-")) {
                return left - right;
            }
            if (tree.getValue().equals("*")) {
                return left * right;
            }
            if (tree.getValue().equals("/")) {
                if (right == 0) {
                    throw new ArithmeticException("Unable to divide by 0."); //cannot divide by 0
                } else {
                    return left / right;
                }
            }
            if (tree.getValue().equals("%")) {
                return left % right;
            }
            else {
                return 0;
            }
        }
    }



    public static String getEquation(bridges.base.BinTreeElement<String> tree){

        if(isNumeric(tree.getValue())){ //base case
            return String.valueOf(tree.getValue());
        }
        else {
            String left = getEquation(tree.getLeft());
            String right = getEquation(tree.getRight());

            if (tree.getValue().equals("+")) {
                return "( " + left + " + " + right + " )";
            }
            if (tree.getValue().equals("-")) {
                return "( " + left +" - " + right + " )";
            }
            if (tree.getValue().equals("*")) {
                return "( " + left +" * "+ right + " )";
            }
            if (tree.getValue().equals("/")) {
                    return "( " + left +" / "+ right + " )" ;
            }
            if (tree.getValue().equals("%")) {
                return "( " + left +" % "+ right + " )";
            }
            else {
                return "";
            }
        }

    }

    public static void main(String[] args){
        String ex1 = "( ( 7 + 3 ) * ( 5 - 2 ) )";
        BinTreeElement<String> parseTree1 = buildParseTree(ex1);
        double answer1 = evaluate(parseTree1);
        System.out.println(answer1);
        System.out.println(getEquation(parseTree1));

        String ex2 = "( ( 10 + 5 ) * 3 )";
        BinTreeElement<String>  parseTree2 = buildParseTree(ex2);
        double answer2 = evaluate(parseTree2);
        System.out.println(answer2);
        System.out.println(getEquation(parseTree2));

        String ex3 = "( ( ( ( ( 2 * 12 ) / 6 ) + 3 ) - 17 ) + ( 2 * 0 ) )";
        BinTreeElement<String>  parseTree3 = buildParseTree(ex3);
        double answer3 = evaluate(parseTree3);
        System.out.println(answer3);
        System.out.println(getEquation(parseTree3));

        String ex4 = "( 3 + ( 4 * 5 ) )";
        BinTreeElement<String>  parseTree4 = buildParseTree(ex4);
        double answer4 = evaluate(parseTree4);
        System.out.println(answer4);
        System.out.println(getEquation(parseTree4));

        /* Initialize a Bridges connection */
        Bridges bridges = new Bridges(5,  "lightfootjb","893184975303" );

        /* Set an assignment title */
        bridges.setTitle("Arithmetic Parse Tree Project - Debra Duke");
        bridges.setDescription("CMSC 256, Spring 2022");

        try {
            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree1);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree2);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree3);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree4);
            /* Visualize the Array */
            bridges.visualize();
        }
        catch(Exception ex){
            System.out.println("Error connecting to Bridges server.");
        }
    }


}
