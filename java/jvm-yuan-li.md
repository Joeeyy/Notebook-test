# JVM 原理



## 0x00 何谓JVM

JVM，Java Virtual Machine（Java虚拟机），主要包括一套字节码指令集、一组寄存器、一个栈、一个垃圾回收堆和一个存储方法域。JVM屏蔽了代码与具体操作系统平台相关的信息，运行Java程序时只需生成在JVM上运行的字节码即可。JVM在执行字节码时，事实上还是将字节码解释称具体平台上的机器指令执行。总体来说，JVM是一个虚拟出来执行Java程序的计算机。

## 0x01 JVM原理

JVM是Java的核心和基础，是处于Java编译器和操作系统平台之间的一个虚拟处理器，用来运行Java字节码程序。Java代码（.java文件）经过Java编译器编译后生成JVM理解的字节码文件（.class文件），由JVM具体运行。





