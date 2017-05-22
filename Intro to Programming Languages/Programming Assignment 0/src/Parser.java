import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by Nathan Purpura on 9/26/2016.
 */
public class Parser {

    private static HashMap<String, ArrayList<String>> ruleSet;
    private static ArrayList<String> parseStack;

    public static void main(String[] args) throws FileNotFoundException
    {
            if(args.length != 2){
                //System.out.println("Expected 2 arguments");
                System.exit(-1);
            }
            String gramFileName = args[0];
            String exprFileName = args[1];

            File gramFile = new File(gramFileName);
            File exprFile = new File(exprFileName);

            if(!gramFile.exists())
            {
                //System.out.println("The Grammar file '"+gramFileName+"' does not exist");
                System.exit(-1);
            }
            if(!exprFile.exists())
            {
                //System.out.println("The Expression file '"+exprFileName+"' does not exist");
                System.exit(-1);
            }
            Scanner gramScan = new Scanner(new FileInputStream(gramFile));
            Scanner exprScan = new Scanner(new FileInputStream(exprFile));

            String rules = "";
            String expression = "";

        while (gramScan.hasNext()) {
            rules += gramScan.nextLine() + "\n";
        }
        while (exprScan.hasNext()) {
            expression += exprScan.nextLine() + "\n";
        }
            parseRules(rules);
        //for (String key : ruleSet.keySet()) {
                //System.out.println("RuleSet: "+key);

        //for (String str : ruleSet.get(key)) {

        //System.out.println("-"+str);

            //}
        //}
            boolean result = parse(expression);
        System.out.println((result ? "string is valid!" : "string is invalid!"));
            //System.out.println(parseStack.size());
        //for (String str : parseStack) {
                //System.out.println("Token: "+str);
        //}
            //System.out.println("Parsing was "+(result ? "a success!" : "a failure..."));
    }

    private static boolean parse(String expression){
        parseStack = new ArrayList<>();
        String[] tokens = expression.split(" ");
        for(String token : tokens)
        {
            //System.out.println("Parsing token: "+token);
            token = token.trim();
            for(String rule : ruleSet.keySet()){
                //System.out.println("i");
                if(rule.equals(token)){
                    parseStack.add(token);
                    //System.out.println("Token match: "+token);
                    break;
                }
                for(String subRule : ruleSet.get(rule))
                {
                    String[] subRuleTokens = subRule.split(" ");
                    for(String subRuleToken : subRuleTokens){
                        if(subRuleToken.equals(token))
                        {
                            //System.out.println("Adding token:"+token);
                            parseStack.add(token);
                            break;
                        }
                    }
                }
            }


        }
        while(reduceStack2()) ;
        return parseStack.size() == 1;

    }

    private static void parseRules(String rules)
    {
        ruleSet = new HashMap<>();
        String[] lines = rules.split("\n");
        String ruleName = "";
        for(String str : lines)
        {
            if(str.startsWith("|")) //For sub rules
            {
                ruleSet.get(ruleName).add(str.substring(1).trim());
            }
            else // For main rules and first sub rule definition
            {

                String subRule1;
                int equalLocation = str.indexOf('=');
                ruleName = str.substring(0, equalLocation-1).trim();
                subRule1 = str.substring(equalLocation+1,str.length()).trim();
                ArrayList<String> newRule = new ArrayList<>();
                newRule.add(subRule1);
                ruleSet.put(ruleName,newRule);

            }
        }
    }



    private static boolean reduceStack2(){

        ArrayList<String> tempStack = new ArrayList<>();
        boolean reduced = false;
        for(String token : parseStack)
        {
            tempStack.add(token);
            for(String superRule : ruleSet.keySet())
            {
                for(String subRule : ruleSet.get(superRule))
                {
                    String[] subRuleTokens = subRule.split(" ");
                    if(tempStack.size() >= subRuleTokens.length)
                    {
                        String firstTokenPatternMatch = subRuleTokens[0];
                        //System.out.println("Attempting to match '"+subRule+"'");
                        for(int firstIndex = 0; firstIndex < tempStack.size(); firstIndex++){
                            if(tempStack.get(firstIndex).equals(firstTokenPatternMatch)){
                                boolean matched = true;
                                int secondIndex;
                                for(secondIndex = firstIndex; secondIndex < firstIndex + subRuleTokens.length; secondIndex++)
                                {
                                    if(secondIndex >= tempStack.size())
                                    {
                                        //System.out.println("Cant: "+secondIndex+", "+tempStack.size());
                                        matched = false;
                                        break;
                                    }
                                    String tempStackToken = tempStack.get(secondIndex);
                                    String subRuleToken   = subRuleTokens[secondIndex-firstIndex];
                                    if(!tempStackToken.equals(subRuleToken)){
                                        //System.out.println("mismatch: "+tempStackToken+", "+subRuleToken+"; for rule="+subRule+" @"+secondIndex);
                                        //for (String str : tempStack) {
                                            //System.out.println("Resulting: "+str);
                                        //}

                                        matched = false; break;
                                    }
                                }
                                if(matched){
                                    reduced = true;
                                    //System.out.println("Matched: "+firstIndex+", "+secondIndex);
                                    ArrayList<String> recreatedStack = new ArrayList<>();
                                    boolean addOne = true;
                                    for(int index = 0; index < tempStack.size(); index++)
                                    {
                                        if(index < firstIndex || index > secondIndex-1)
                                        {
                                            //System.out.println("Adding asdsad "+tempStack.get(index) );
                                            recreatedStack.add(tempStack.get(index));
                                        }
                                        else
                                        {
                                            if(addOne){recreatedStack.add(superRule); addOne = false;}

                                        }
                                    }
                                    parseStack = recreatedStack;
                                    //for (String str : parseStack) {
                                        //System.out.println("Resulting: "+str);
                                    //}
                                }
                            }
                        }
                    }
                }
            }
        }
        return reduced;

    }



}
