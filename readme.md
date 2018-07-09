This application enables client and developer to use and execute zip code range merging.

1. Data structures and algorithm:
The input contains a collection of lower bond and upper bond zip code pairs, so we defined them as class RangeNode with these information,
and each node contains a left child, which represents a range smaller than itself, right child representing a range bigger than itself.
We collect them from String into an arrayList of RangeNodes, and then construct a binary search tree based on the ranges these nodes have.
The output is a customized binary-search-tree, based on the ranges.
The algorithm is basically a binary-search-tree in-order traversal. 
To balance the worst case scenario, we randomly pick one range from the input as the root of the binary search tree.


2. Package contents:
package has the following directories:
lib: external libraries used for this application's execution and testing
resources: a file that contains a relatively big String as input.
src: java classes that make the application
test: J-Unit test class that contains several test cases 


Author: Chad Zhou
1.0 release date: 07/02/2018