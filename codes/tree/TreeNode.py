#coding=utf8

class TreeNode():
    def __init__(self, value, left=None, right=None):
        print("new TreeNode ",value)
        self.value = value
        self.left = left
        self.right = right

    def setLeft(self, left):
        self.left = left

    def setRight(self, right):
        self.right = right

    def printInfo(self):
        if self.left == None and self.right == None:
            print("Tree %d, with left-0 and right-0"%(self.value))
        elif self.left == None:
            print("Tree %d, with left-0 and right-%d"%(self.value,self.right.value))
        elif self.right == None:
            print("Tree %d, with left-%d and right-0"%(self.value,self.left.value))
        else:
            print("Tree %d, with left-%d and right-%d"%(self.value,self.left.value,self.right.value))
