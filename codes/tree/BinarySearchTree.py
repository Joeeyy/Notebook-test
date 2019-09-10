#coding=utf8

from TreeNode import TreeNode


'''
> refer: https://zh.wikipedia.org/wiki/二元搜尋樹

二叉查找树，又称二叉搜索树、有序二叉树或排序二叉树，是指一个空二叉树或者有以下性质的二叉树：
1. 若任意节点左子树不为空，则该左子树所有节点值均小于其根节点值；
2. 若任意节点右子树不为空，则该右子树所有节点值均大于其根节点值；
3. 任意节点的左右子树也都是二叉查找树；
4. 没有键值相等的节点。

优势：查找、插入时间复杂度较低，为O(logn)
'''


def build_binary_search_tree(values):
    tree = None
    for value in values:
        tree = binary_search_tree_insert(tree,value)

    print(tree)
    return tree

def binary_search_tree_search(tree, x):
    print(tree.value)
    if tree == None:
        return False

    if x == tree.value:
        return True

    if x < tree.value:
        return binary_search_tree_search(tree.left,x)
    else:
        return binary_search_tree_search(tree.right,x)

def binary_search_tree_insert(tree, e):
    if tree == None:
        tmptree = TreeNode(e)
        tmptree.left = tmptree.right = None
        tree = tmptree
        return tree
    elif e == tree.value:
        return False

    if e < tree.value:
        binary_search_tree_insert(tree.left,e)
    else:
        binary_search_tree_insert(tree.right,e)
    return True


def main():
    print("binary search tree.")
    array = [1,2,3,4,5,6,7,8]
    tree = build_binary_search_tree(array)
    print(binary_search_tree_search(tree,6))

if __name__ == '__main__':
    main()
