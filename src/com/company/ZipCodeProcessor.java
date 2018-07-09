package com.company;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*
    This class defines operations can be performed to process zip code input and generate output
    Author: Chad Zhou
    Date: 06/28/2018
 */


public class ZipCodeProcessor {

    public ZipCodeProcessor() {

    }

    /*
        This method describes the basic flow of generating the output
     */
    
    public void generateOutput(String input) {
        ArrayList<RangeNode> rangeNodes = processInput(input);
        RangeNode result = generateRangeTree(rangeNodes);
        printResult(result);

    }

    /*
        This method takes string as user input,
        and produces a list of range nodes,
        returns the ArrayList that contains all the nodes
     */
    private ArrayList<RangeNode> processInput(String input) {

        String[] splitInput = input.trim().replaceAll(" +", " ").split(" ");
        ArrayList<RangeNode> rangeNodes = new ArrayList<>();

        // add each range as RangeNode, along with 3 levels of validation
        for (int i = 0; i < splitInput.length; i++) {
            String range = splitInput[i];
            if (range.length() != 13) {
                System.err.println("zip code format error");
                break;
            }
            else {
                String[] rangePair = range.substring(1, range.length() - 1).split(",");
                if (rangePair.length != 2) {
                    System.err.println("zip code bound is incomplete in input");
                    break;
                } else {
                    int min = Integer.parseInt(rangePair[0]);
                    int max = Integer.parseInt(rangePair[1]);
                    if (min > max) {
                        System.err.println("lower_bound cannot be bigger than upper_bound");
                        break;
                    }
                    rangeNodes.add(new RangeNode(min, max));
                }
            }
        }

        return rangeNodes;
    }

    /*
        This method takes the root of RangeNode tree, 
        and prints the tree as the result
     */

    private void printResult(RangeNode root) {
        if (root != null) {
            printResult(root.getLeftChild());

            String minString = Integer.toString(root.getMin());
            String maxString = Integer.toString(root.getMax());
            if (minString.length() < 5) {
                StringBuilder  sb_min= new StringBuilder(minString);
                for (int i = 0; i < 5 - minString.length(); i++) {
                    sb_min.insert(0, "0");
                }
                minString = sb_min.toString();
            }

            if (maxString.length() < 5) {
                StringBuilder  sb_min= new StringBuilder(maxString);
                for (int i = 0; i < 5 - maxString.length(); i++) {
                    sb_min.insert(0, "0");
                }
                maxString = sb_min.toString();
            }

            System.out.print("[" + minString + "," + maxString + "] ");
            printResult(root.getRightChild());
        }
    }

    /*
        This method takes all the range nodes, 
        and makes them into a binary search RangeNode tree,
        returns the root of the tree
     */
    private RangeNode generateRangeTree(ArrayList<RangeNode> rangeArrayList) {
        if (rangeArrayList == null || rangeArrayList.size() == 0) {
            return null;
        }
        int indexRoot = ThreadLocalRandom.current().nextInt(0, rangeArrayList.size());
        RangeNode root = rangeArrayList.get(indexRoot);
        for (int i = 0; i < rangeArrayList.size(); i++) {

                RangeNode currentNode = rangeArrayList.get(i);
                insertPair(root, currentNode.getMin(), currentNode.getMax());

        }
        return rangeArrayList.get(indexRoot);
    }

    /*
        This method takes the root of the RangeNode tree, a new pair of [lower_bound, upper_bound],
        and insert as a new tree node.
        returns the root of the tree
     */
    private RangeNode insertPair(RangeNode root, int newMin, int newMax) {

        if (root == null) {
            root = new RangeNode(newMin, newMax);
        }

        // we split into different conditions
        // 1. the new range is within the range of current node, do not insert new node, do nothing

        // 2. new range is completely off from root's lower bound
        if (newMax < root.getMin() || newMin > root.getMax()) {
            if (newMax < root.getMin()) {
                RangeNode newLeftChild = insertPair(root.getLeftChild(), newMin, newMax);
                root.setLeftChild(newLeftChild);
            }
            if (newMin > root.getMax()) {
                RangeNode newRightChild = insertPair(root.getRightChild(), newMin, newMax);
                root.setRightChild(newRightChild);
            }
        }

        else {
            // 3. the one side of root's range has extended, or has overlap with its left
            if (newMin < root.getMin()) {
                // 3.1 root has a leftChild
                if (root.getLeftChild() != null) {
                    // 3.1.1 covers all the range its leftChild has
                    if (newMin <= root.getLeftChild().getMin()) {
                        root.setMin(newMin);
                        root.setLeftChild(null);
                    }
                    // 3.1.2 overlaps with its leftChild so that its leftChild's range merges root's range
                    else if (newMin <= root.getLeftChild().getMax()) {
                        root.setMin(root.getLeftChild().getMin());
                        root.setLeftChild(null);
                    }
                    // 3.1.3 does not overlap with leftChild
                    else {
                        root.setMin(newMin);
                    }
                }
                // 3.2 root does not have a leftChild
                else {
                    root.setMin(newMin);
                }
            }
            // likewise on right side
            if (newMax > root.getMax()) {
                if (root.getRightChild() != null) {
                    if (newMax >= root.getRightChild().getMax()) {
                        root.setMax(newMax);
                        root.setRightChild(null);
                    } else if (newMax >= root.getRightChild().getMin()) {
                        root.setMax(root.getRightChild().getMax());
                        root.setRightChild(null);
                    } else {
                        root.setMax(newMax);
                    }
                } else {
                    root.setMax(newMax);
                }
            }

        }
        return root;
    }
}
