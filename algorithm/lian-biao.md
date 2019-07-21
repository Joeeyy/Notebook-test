# 链表

## 简介

基础数据结构之一，是一种线性表，插入元素时间复杂度为O\(1\)，查询、访问指定元素的时间复杂度为O\(n\)。作为比较的，顺序存储的线性数据结构数组的查询时间复杂度为O\(logn\)，访问的时间复杂度为O\(1\)。

链表结构克服了数组需要预先知道数据长度的问题，可以重复利用计算机内存空间，实现内存动态管理。

然而，链表由于加入了指针域，空间开销比较大。

常见的链表种类有：单向链表，双向链表，循环链表，块状链表。

## 简单实现

一个链表节点：

```py
class Node:
    def __init__(self,node_value,next_node=None):
        self.node_value = node_value
        self.next_node = next_node
```

一个简单的链表对象：

```py
class linked_list:
    def __init__(self,seq=[]):
        self.head_node = Node()
        self.length = len(seq)
        current_node = None
        for e in seq:
            tmp_node = Node(e)
            if self.head_node.next_node == None:
                self.head_node.next_node = tmp_node
            else:
                current_node.next_node = tmp_node
            current_node = tmp_node
```

用一个数组初始化上面的链表：

```py
ll = linked_list([1,2,3,4,5])
```

打印链表信息（包含链表遍历，能遍历也就意味着能查找元素）：

```py
    def printInfo(self):
        info_format = "LinkedList with length {}: {}"
        node_str = ""
        node_num = 0
        current_node = self.head_node
        while current_node.next_node:
            node_num += 1
            current_node = current_node.next_node
            if current_node.next_node == None:
                node_str += "{}".format(current_node.node_value)
            else:
                node_str += "{}->".format(current_node.node_value)

        print(info_format.format(node_num,node_str))
```

插入元素：

```py
    def insertAt(self,index, value):
        if index < 0:
            raise Exception("invalid index")
        if index > self.length:
            raise Exception("Out of bound")

        current_node = self.head_node
        counter = 0
        while(current_node.next_node):
            if counter == index:
                tmp_node = Node(node_value=value)
                tmp_node.next_node = current_node.next_node
                current_node.next_node = tmp_node
                self.length += 1
                return
            current_node = current_node.next_node
            counter += 1
```

删除元素：

```py
    def deleteAt(self,index):
        if index < 0:
            raise Exception("invalid index")
        if index >= self.length:
            raise Exception("Out of bound")

        current_node = self.head_node
        counter = 0
        while(current_node.next_node):
            if counter == index:
                tmp_node = current_node.next_node.next_node
                current_node.next_node = tmp_node
                self.length -= 1
                return
            current_node = current_node.next_node
            counter += 1
```

链表翻转：

```py
    def reverse(self):
        if self.length <= 1:
            return 

        prev_node = self.head_node
        current_node = prev_node.next_node
        while(current_node):
            next_node = current_node.next_node
            if prev_node.node_value == None:
                current_node.next_node = None
            else:
                current_node.next_node = prev_node
            prev_node = current_node
            current_node = next_node

        self.head_node.next_node = prev_node
```

链表的两两翻转，如`1->2->3->4`翻转为`2->1->4->3`：

```py
    def reverse2(self):
        if self.length <= 1:
            return 

        prev_node = self.head_node
        current_node = prev_node.next_node

        while(current_node.next_node):
            next_next_node = current_node.next_node.next_node
            prev_node.next_node = current_node.next_node
            prev_node.next_node.next_node = current_node
            prev_node = prev_node.next_node.next_node
            current_node.next_node = next_next_node
            current_node = next_next_node
            if current_
```

> 更多关于链表考点可以参考：[面试精选：链表问题集锦](http://wuchong.me/blog/2014/03/25/interview-link-questions/)



