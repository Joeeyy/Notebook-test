<?php
    class test{
        var $a = "123";
        function print_info(){
            echo "class test\n";
        }
        function __wakeup(){
            echo "wake up \n";
        }
        function __construct(){
            echo "construct \n";
        }
        function __destruct(){
            echo "destruct \n";
        }
    }

    $test1 = new test();
    $test1->print_info();
    $serialized_string = serialize($test1);
    echo $serialized_string. "\n";
    $test2 = unserialize($serialized_string);
    $test2->print_info();
?>
