package com.jason.calculate163;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.*;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplayMessageActivity extends AppCompatActivity {

    ArrayList<String> solutions = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Additional results not yet implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(20);

        message = main(message);


        textView.setText(message);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);
    }

    public String main(String args){
        String input = args;
        String output = "";

        try {
            //count commas
            int n = input.length() - input.replace(",", "").length();

            //Find the values

            int[] numbers = new int[n + 1];
            for (int i = 0; i < n; i++) {
                int commaIndex = input.indexOf(',');
                int temp = Integer.parseInt(input.substring(0, commaIndex));
                numbers[i] = temp;
                input = input.substring(commaIndex + 1);
            }
            numbers[n] = Integer.parseInt(input);

            Arrays.sort(numbers);

            ArrayList<String> solutions = generatePermutations(numbers, 0);

            if(solutions.size()==0){
                output += "No solutions found :(";
            }else{
                output += "Solution:";

                int count = 0;
                while(count<5 && solutions.size()>0){
                    output+="\n"+solutions.get(0);
                    solutions.remove(0);
                    count++;
                }
            }
        }catch(Exception e){
            output = "yoxu fucked something up :(\nhere are some details";
            output += "\nException type: " + e.getClass();
            output += "\nException message: " + e.getMessage();
        }

        output += "\n\nFor additional possible solutions, press the \"plus\" button";

        return output;


    }

    public ArrayList<String> generatePermutations(int [] input, int index){

        ArrayList<String> solutions = new ArrayList<String>();

        //base case
        if(input.length -1 == index){
            String nums = "" + input[0];
            for(int i=1; i<input.length; i++)
                nums+="x"+input[i];
            solutions.addAll(addOperators(nums));
            if(solutions.size()>0)
                return solutions;
        }
        else{
            for(int i=index; i<input.length; i++){
                if(solutions.size()>0)
                    return solutions;
                if(i==index){
                    solutions.addAll(generatePermutations(input,index+1));

                }else{
                    int temp = input[index];
                    input[index]=input[i];
                    input[i] = temp;
                    solutions.addAll(generatePermutations(input,index+1));
                    //return solutions;
                }

            }

        }
        return solutions;
    }

    public ArrayList<String> addOperators(String input){
        ArrayList<String> solutions = new ArrayList<String>();
        if(input.indexOf('x')==-1){
            if(eval(input)==163) {
                solutions.add(input+"=163");
                //System.out.println(input);
                return solutions;
            }
        }else{
            int index = input.indexOf('x');
            String beginning = input.substring(0,index);
            String end = input.substring(index+1,input.length());
            solutions.addAll(addOperators(beginning + '+' + end));
            solutions.addAll(addOperators(beginning + '-' + end));
            solutions.addAll(addOperators(beginning + '*' + end));
            solutions.addAll(addOperators(beginning + '/' + end));
        }
        return solutions;
    }

    /**
     * Takes a string of mathematical operators and numbers. Includes parentheticals. Returns the answer
     * evaluated from left to right
     *
     * @param initialInput String of text. eg: 7+(4+3)*2/4+1
     * @return numerical answer. eg: 8
     */
    public double eval(String initialInput){

        String input = initialInput;

        //Replace T with 10, J with 11, Q with 12, K with 13
        input = input.replace("T","10");
        input = input.replace("J","11");
        input = input.replace("Q","12");
        input = input.replace("K","13");

        //Replace parentheticals with evaluated values
        while(input.indexOf(')')!=-1){
            int indexOfRightParen = input.indexOf(')');
            int indexOfLeftParen = input.lastIndexOf('(',indexOfRightParen);

            String tempInput = "";
            tempInput += input.substring(0,indexOfLeftParen);
            tempInput += eval2(input.substring(indexOfLeftParen+1,indexOfRightParen));
            tempInput += input.substring(indexOfRightParen+1);
            input = tempInput;
        }
        return eval2(input);
    }

    /**
     * Takes a string of mathematical operators and numbers. Assumes no parentheses. Returns answer evaluated from
     * left to right
     *
     * @param initialInput  String of text
     * @return  numeral solution
     */
    public double eval2(String initialInput){
        String input = initialInput;

        //find indices of operators
        ArrayList<Integer> indices = new ArrayList <Integer>();
        String input2 = input;
        while(input.indexOf('+')!=-1){
            indices.add(input.indexOf('+'));
            input = input.substring(0,input.indexOf('+'))+'x'+input.substring(input.indexOf('+')+1);
            //System.out.println(input);
        }
        while(input.indexOf('-')!=-1){
            indices.add(input.indexOf('-'));
            input = input.substring(0,input.indexOf('-'))+'x'+input.substring(input.indexOf('-')+1);
        }
        while(input.indexOf('*')!=-1){
            indices.add(input.indexOf('*'));
            input = input.substring(0,input.indexOf('*'))+'x'+input.substring(input.indexOf('*')+1);
            //System.out.println(input);

        }
        while(input.indexOf('/')!=-1){
            indices.add(input.indexOf('/'));
            input = input.substring(0,input.indexOf('/'))+'x'+input.substring(input.indexOf('/')+1);

        }
        //System.out.println(indices.toString());
        Integer[] indices2 = indices.toArray(new Integer[indices.size()]);

        //System.out.println(Arrays.toString(indices2));

        Arrays.sort(indices2);

        //System.out.println(Arrays.toString(indices2));

        double solution =0;
        if(indices2.length!=0)
            solution=Double.parseDouble(input2.substring(0,indices2[0]));
        else {
            solution = Double.parseDouble(input2);
            return solution;
        }
        //System.out.println(solution);

        for(int i=0; i<indices2.length; i++){
            //base case
            double nextNum = 0;
            if(i==indices2.length-1){
                nextNum = Double.parseDouble(input2.substring(indices2[i]+1));
            }else {
                nextNum = Double.parseDouble(input.substring(indices2[i] + 1, indices2[i + 1]));
            }

            switch(input2.charAt(indices2[i])){
                case '+': solution += nextNum;
                    break;
                case '-': solution -= nextNum;
                    break;
                case '*': solution *= nextNum;
                    break;
                case '/': solution /= nextNum;
                    break;
                default: solution = 0;
                    break;
            }
            //System.out.println(solution);
        }


        return solution;
    }



}
