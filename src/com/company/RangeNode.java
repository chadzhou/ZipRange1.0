package com.company;

/*
    This class defines RangeNode as the node of a binary search tree
    Author: Chad Zhou
    Date: 06/28/2018
 */
public class RangeNode {

    private int min;
    private int max;
    private RangeNode leftChild;
    private RangeNode rightChild;

    public RangeNode(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public RangeNode getLeftChild() {
        return leftChild;
    }

    public RangeNode getRightChild() {
        return rightChild;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setLeftChild(RangeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(RangeNode rightChild) {
        this.rightChild = rightChild;
    }
}
