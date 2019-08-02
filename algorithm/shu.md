# 树

## 二叉树

### 二叉树的定义

我们可以这样定义一个二叉树节点：

```py
class TreeNode():
    def __init__(self, value, left=None, right=None):
        print("new TreeNode %d"%value)
        self.value = value
        self.left = left
        self.right = right

    def setLeft(self, left):
        self.left = left

    def setRight(self, right):
        self.right = right
```

再通过创建节点，将其拼接成为一个二叉树：

```py
def main():
    print("tree, Binary Tree... ")
    treeNode1 = TreeNode(1)
    treeNode2 = TreeNode(2)
    treeNode3 = TreeNode(3)
    treeNode4 = TreeNode(4)
    treeNode5 = TreeNode(5)
    treeNode6 = TreeNode(6)
    treeNode7 = TreeNode(7)
    treeNode8 = TreeNode(8)

    treeNode1.setLeft(treeNode2)
    treeNode1.setRight(treeNode3)
    treeNode2.setLeft(treeNode4)
    treeNode2.setRight(treeNode5)
    treeNode5.setLeft(treeNode7)
    treeNode5.setRight(treeNode8)
    treeNode3.setRight(treeNode6)
```

这样，treeNode1就是其根节点。

### 二叉树的遍历

#### 前序遍历

```py
'''
root left right
'''
def preOrderTraverse(rootNode):
    if rootNode == None:
        return
    #print("start preOrderTraverse from treeNode - %d"%treeNode.value)
    print(rootNode.value, end=" ")
    preOrderTraverse(rootNode.left)
    preOrderTraverse(rootNode.right)

def preOrderTraverse2(rootNode):
    if rootNode == None:
        return

    stack = []
    currentNode = rootNode
    while(len(stack)>0 or currentNode != None):
        if currentNode != None:
            print(currentNode.value, end=" ")
            stack.append(currentNode)
            currentNode = currentNode.left
        else:
            currentNode = stack.pop().right
```

#### 中序遍历

```py
'''
left root right
'''
def middleOrderTraverse(rootNode):
    if rootNode == None:
        return

    middleOrderTraverse(rootNode.left)
    print(rootNode.value, end=" ")
    middleOrderTraverse(rootNode.right)

def middleOrderTraverse2(rootNode):
    if rootNode == None:
        return

    stack = []
    currentNode = rootNode

    while(len(stack)!=0 or currentNode!=None):
        if currentNode != None:
            stack.append(currentNode)
            currentNode = currentNode.left
        else:
            currentNode = stack.pop()
            print(currentNode.value, end=" ")
            currentNode = currentNode.right
```

#### 后序遍历

```py
'''
left right root
'''
def postOrderTraverse(rootNode):
    if rootNode == None:
        return

    postOrderTraverse(rootNode.left)
    postOrderTraverse(rootNode.right)
    print(rootNode.value, end=" ")

def postOrderTraverse2(rootNode):
    if rootNode == None:
        return

    stack = []
    leftVisitedStack = []
    currentNode = rootNode
    fromRight = 0
    counter = 0

    while(len(stack)!=0 or currentNode != None):
        counter += 1
        if currentNode != None:
            stack.append(currentNode)
            currentNode = currentNode.left
        else:
            currentNode = stack[-1]
            while len(leftVisitedStack) > 0 and currentNode == leftVisitedStack[-1]:
                print(currentNode.value, end=" ")
                stack.pop()
                leftVisitedStack.pop()
                if len(stack) > 0:
                    currentNode = stack[-1]
                else:
                    return
            leftVisitedStack.append(currentNode)
            currentNode = currentNode.right

```

#### 层次遍历

```py
def levelTraverse(rootNode):
    if rootNode == None:
        return

    queue = []
    queue.append(rootNode)

    while(len(queue) > 0):
        node = queue.pop(0)
        print(node.value, end=" ")
        if node.left != None:
            queue.append(node.left)
        if node.right != None:
            queue.append(node.right)
```





