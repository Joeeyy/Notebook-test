<?php
    class test{
        var $a = "123";
        function print_info(){
            echo "class test\n";
        }
    }

    $test1 = new test();
    $test1->print_info();
    $serialized_string = serialize($test1);
    echo $serialized_string. "\n";
    $test2 = unserialize($serialized_string);
    $test2->print_info();
?>
