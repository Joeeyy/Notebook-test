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



