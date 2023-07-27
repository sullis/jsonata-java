package com.dashjoin.jsonata;

import java.util.List;

import static com.dashjoin.jsonata.Jsonata.jsonata;

public class Bench {

    public static void main(String[] args) throws Throwable {
        String s;
        //s = "$join(['a','b','c'], '#')";
        //s = "$count([1..(1e4-1)])";
        //s = "{ 'number': [1..10].$string() }";
        //s = "[1..10].($ * $).$sum()";
        s = "($a := [1..10].($ * $); $sum($a) )";
        // s = "$substringBefore(\"Alalala\", \"la\")";
        // s = "$substring(\"Alalala\", 1,4)";
        // s = "$pad('xxx', -5, 'abrac')";
        // // group matches:
        //s = "$match(\"Alalalcl\", /l(a|c)/)";
        // s = "$match(\"Alalalcl\", /l(a|c)(l|x)/)";

        //s = "[0,1,2].$boolean()";

        //s = "$replace('abcdef', /c.*/, 'xy')";
        //s = "$replace('mad hatter', /hat/i, function($match) { 'foo' })";
        // s = "$split('abcdef', /c./)";
        //s = "$split('abcdef', 'cd')";

        //s = "([1..1e5])";
        //s = "$string({'a':[1,2]}, true)";
        //s = "$number('3.1e1')";

        //s = "$map(['11','1e5','0.00001'], $number ~> $sqrt )";

        //s = "(  $data := {    \"one\": [1,2,3,4,5],    \"two\": [5,4,3,2,1]  };  $add := function($x){$x*$x};  $map($data.two, $add) ~> $sum )  ";
        //s = "(  $data := {    \"one\": [1,2,3,4,5],    \"two\": [5,4,3,2,1]  };  $add := function($x){$x*$x};  $data.one )  ";

        //s = "$zip([1,2], [3,4])";
        //s = "$zip([1,2], [3,4,5], null)";

        //s = "$keys([{'a':true},{'b':true}])";

        //s = "$each({'a':1, 'b':2}, $string)";

        //s = "$formatBase(100*$random(), 2)";

        //s = "$clone({'a':[1.0,2,3.5]}).a";

        //s = "$eval('[1,2].$string()')";
        //s = "false > 5";
        //s = "10e300 * 10e100";
        //s="{'a':()}";
        //s="{'a':$}";
        //s = "$#$pos[$pos<3] = $[[0..2]]";
        //s = "$.λ($num){$number($trim($string($num)))*$num}($)";
        //s = "$.($*$)";
        //s = "[1..5].λ($num){$number($num)}";
        //s = "($f := function($x)<s:s> { $x };  $f(1)  )";
        //s = "$sift({'a':42, 'b':41}, function($e) { $e<42 })";
        s = "$sift({'a': 'hello', 'b': 'world', 'hello': 'again'}, λ($v,$k,$o){$lookup($o, $v)})";
        //s = "1+2";
        //s = "$map([1], $string)[]";
        //s = "(  $data := {    \"one\": [1,2,3,4,5],    \"two\": [5,4,3,2,1]  };  $add := function($x){$x*$x};  $data.one )  ";
        if (args.length>0)
            s = args[0];

        long duration = 5000L;

        if (args.length>1)
            duration = Long.valueOf(args[1]);

        Object input = List.of(1,2,3,4,5,6);

        System.out.println("Expression = "+s);
        System.out.println("Duration = "+duration+"ms");

        long t0 = System.currentTimeMillis();
        Object result = null;
        Jsonata jsonata = null;
        int i, count1, count2;
        for (i=0; ; i++) {
            jsonata = jsonata(s);
            if ((i%10)==0) {
                if ((System.currentTimeMillis()-t0)>duration)
                    break;
            }
        }
        count1 = i;
        long t1 = System.currentTimeMillis();

        System.out.println("Parse N="+count1+" T="+(t1-t0)+" OpsPerSec="+(1000d*count1/(t1-t0)));

        for (i=0; ; i++) {
            result = jsonata.evaluate(input, null);
            if ((i%10)==0) {
                if ((System.currentTimeMillis()-t1)>duration)
                    break;
            }
        }
        count2 = i;
        long t2 = System.currentTimeMillis();

        //System.out.println("Total T="+(t2-t0)+" PerSec="+(1000000000L/(t2-t0)));
        System.out.println("Eval  N="+count2+" T="+(t2-t1)+" OpsPerSec="+(1000d*count2/(t2-t1)));
        //Object result = jsonata.evaluate(new JList(List.of(3,1,4,1,5,9)), null);
        System.out.println("Result = "+Functions.string(result, true));
    }
}
