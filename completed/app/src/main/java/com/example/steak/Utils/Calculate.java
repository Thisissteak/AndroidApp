package com.example.steak.Utils;

import android.content.Context;
import android.util.Log;

import com.example.luozenglin.calculator.R;
import com.example.steak.common.InputItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculate {

    public static String getResult(List<InputItem> inputItemList, Context context)
            throws ArithmeticException {
        Stack<String> numberStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        List<InputItem> expressionList = preconditioning(inputItemList);
        for (InputItem item : expressionList) {
            Log.d("Calculate", "InputItem:????? " + item.getValue()+"\n numberStack:?????"
                    +numberStack.toString()+"\n operatorStack: "+operatorStack.toString());
            if (item.getType() == InputItem.TYPE.NUM) {
                numberStack.push(item.getValue());
            } else if (item.getType() == InputItem.TYPE.OPE_NUM) {
                if (item.getValue().equals(context.getString(R.string.PI))) {
                    numberStack.push(String.valueOf(Math.PI));
                } else if (item.getValue().equals(context.getString(R.string.e))) {
                    numberStack.push(String.valueOf(Math.E));
                }
            } else {
                if (item.getType() == InputItem.TYPE.LEFT_BRACKET) {
                    operatorStack.push(item.getValue());
                } else if (item.getType() == InputItem.TYPE.RIGHT_BRACKET) {
                    while (!operatorStack.empty() && !operatorStack.peek().equals(context.getString(R.string.leftBra))) {
                        processAnOperator(numberStack, operatorStack, context);
                        }
                        if(!operatorStack.empty()){
                        operatorStack.pop();
                        }
                } else if (item.getType() != InputItem.TYPE.OPE || item.getValue().equals(context.getString(R.string.power))) {
                    while (!operatorStack.empty() && !operatorStack.peek().equals("+") &&
                            !operatorStack.peek().equals("-") && !operatorStack.peek().equals(
                            context.getString(R.string.multiply)) && !operatorStack.peek().equals("+") && !operatorStack.peek().equals(context.getString(R.string.divide))
                            && !operatorStack.peek().equals(context.getString(R.string.leftBra))) {
                        processAnOperator(numberStack, operatorStack, context);
                    }
                    operatorStack.push(item.getValue());
                } else if (item.getValue().equals(context.getString(R.string.multiply)) || item.getValue().equals(context.getString(R.string.divide))) {
                    while (!operatorStack.empty() && !operatorStack.peek().equals("+") &&
                            !operatorStack.peek().equals("-") && !operatorStack.peek().equals(context.getString(R.string.leftBra))) {
                        processAnOperator(numberStack, operatorStack, context);
                    }
                    operatorStack.push(item.getValue());
                } else if (item.getValue().equals("+") || item.getValue().equals("-")) {
                    while (!operatorStack.empty() && !operatorStack.peek().equals(context.getString(R.string.leftBra))) {
                        processAnOperator(numberStack, operatorStack, context);
                    }
                    operatorStack.push(item.getValue());
                }
            }
        }
        while (!operatorStack.isEmpty()) {
            processAnOperator(numberStack, operatorStack, context);
        }
        return dealWithResult(numberStack.pop());
    }

    private static String dealWithResult(String result){
        String res = String.format("%.12f",Double.parseDouble(result));
        for(int i = res.length()-1;i>0;i--){
            if(res.charAt(i)=='.'){
                return res.substring(0,i);
            }else if(res.charAt(i)!='0'){
                break;
            } else {
                res = res.substring(0,i);
            }
        }
        if(res.equals("-0")){
            return "0";
        }
        return res;
    }

    private static void processAnOperator(Stack<String> numberStack, Stack<String> operatorStack,
                                          Context context) throws ArithmeticException {
        String ope = operatorStack.pop();
        Log.i("Calculate", "operator: " + numberStack.peek() + ope);
        if (ope.equals("+")) {
            numberStack.push(new BigDecimal(numberStack.pop()).add(new BigDecimal(numberStack.pop())).toString());
        } else if (ope.equals("-")) {
            BigDecimal bigDecimal = new BigDecimal(numberStack.pop());
            numberStack.push(new BigDecimal(numberStack.pop()).subtract(bigDecimal).toString());
        } else if (ope.equals(context.getString(R.string.multiply))) {
            numberStack.push(new BigDecimal(numberStack.pop()).multiply(new BigDecimal(numberStack.pop())).toString());
        } else if (ope.equals(context.getString(R.string.divide))) {
            BigDecimal bigDecimal1 = new BigDecimal(numberStack.pop());
            BigDecimal bigDecimal2 = new BigDecimal(numberStack.pop());
            try {
                numberStack.push(bigDecimal2.divide(bigDecimal1).toString());
            } catch (ArithmeticException e) {
                Log.e("Calculate", "divide operation : divide " + bigDecimal1 + "\n" + e);
                numberStack.push(bigDecimal2.divide(bigDecimal1, 12, BigDecimal.ROUND_HALF_UP).toString());
            }
        } else if (ope.equals(context.getString(R.string.perCent))) {
            numberStack.push(new BigDecimal(numberStack.pop()).divide(new BigDecimal(100)).toString());
        } else if (ope.equals(context.getString(R.string.sin))) {
            numberStack.push(String.valueOf(Math.sin(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.cos))) {
            numberStack.push(String.valueOf(Math.cos(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.tan))) {
            numberStack.push(String.valueOf(Math.tan(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.power))) {
            double d = Double.parseDouble(numberStack.pop());
            numberStack.push(String.valueOf(Math.pow(Double.parseDouble(numberStack.pop()), d)));
        } else if (ope.equals(context.getString(R.string.log))) {
            numberStack.push(String.valueOf(Math.log10(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.ln))) {
            numberStack.push(String.valueOf(Math.log(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.squareRoot))) {
            numberStack.push(String.valueOf(Math.sqrt(Double.parseDouble(numberStack.pop()))));
        } else if (ope.equals(context.getString(R.string.factorial))) {
            int a = 1;
            double n = Math.floor(Double.parseDouble(numberStack.pop()));
            for (int i = 1; i <= n; i++) {
                a *= i;
            }
            if (n == 0.0) {
                a = 0;
            }
            numberStack.push(String.valueOf(a));
        } else if (ope.equals(context.getString(R.string.reciprocal))) {
            numberStack.push(String.valueOf(Math.pow(Double.parseDouble(numberStack.pop()), -1)));
        }
        Log.i("Calculate", "processAnOperator result: " + numberStack.peek());
    }

    protected static List<InputItem> preconditioning(List<InputItem> inputItemList) {
        List<InputItem> list = new ArrayList<>();
        String numberString = "";
        for (InputItem inputItem : inputItemList) {
            if (inputItem.getType().equals(InputItem.TYPE.NUM)) {
                numberString += inputItem.getValue();
            } else {
                if (!numberString.equals("")) {
                    list.add(new InputItem(numberString, InputItem.TYPE.NUM));
                    numberString = "";
                }
                list.add(inputItem);
            }
        }
        if (!numberString.equals("")) {
            list.add(new InputItem(numberString, InputItem.TYPE.NUM));
        }
        String expression = "";
        for (InputItem inputItem : list) {
            expression += inputItem.getValue();
        }
        Log.i("Calculate", "expression: " + expression);
        return list;
    }
}