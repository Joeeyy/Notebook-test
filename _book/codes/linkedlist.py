#coding=utf8

class Node:
    def __init__(self,node_value=None,next_node=None):
        self.node_value = node_value
        self.next_node = next_node

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
            if current_node == None:
                return
                



def main():
    print("Test chain list")

    ll = linked_list([1,2,3,4,5,6])
    ll.reverse2()
    ll.printInfo()



if __name__ == '__main__':
    main()