/*
 * MIT License
 *
 * Copyright (c) 2022 Geekific (https://www.youtube.com/c/Geekific)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice, Geekific's channel link and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.uchile.logaritmos.rbt;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

public class RedBlackTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public Tree<T> insert(T data) {
        Node<T> node = new Node<>(data);
        root = insert(root, node);
        recolorAndRotate(node);
        return this;
    }

    private Node<T> insert(Node<T> node, Node<T> nodeToInsert) {
        if (node == null) {
            return nodeToInsert;
        }
        if (nodeToInsert.getData().compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        return node;
    }

    private void recolorAndRotate(Node<T> node) {
        Node<T> parent = node.getParent();
        if (node != root && parent.getColor() == RED) {
            Node<T> grandParent = node.getParent().getParent();
            Node<T> uncle = grandParent == null ? null : parent.isLeftChild() ?
                    grandParent.getRightChild() : grandParent.getLeftChild();
            if (uncle != null && uncle.getColor() == RED) { // Recoloring
                handleRecoloring(parent, uncle, grandParent);
            } else if (parent.isLeftChild()) { // Left-Left or Left-Right situation
                handleLeftSituations(node, parent, grandParent);
            } else if (!parent.isLeftChild()) { // Right-Right or Right-Left situation
                handleRightSituations(node, parent, grandParent);
            }
        }
        root.setColor(BLACK); // Color the root node black
    }

    private void handleRightSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (node.isLeftChild()) {
            rotateRight(parent);
        }
        parent.flipColor();
        if(grandParent != null){
            grandParent.flipColor();
            rotateLeft(grandParent);
            recolorAndRotate(node.isLeftChild() ? grandParent : parent);
        }
    }

    private void handleLeftSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (!node.isLeftChild()) {
            rotateLeft(parent);
        }
        parent.flipColor();
        grandParent.flipColor();
        rotateRight(grandParent);
        recolorAndRotate(node.isLeftChild() ? parent : grandParent);
    }

    private void handleRecoloring(Node<T> parent, Node<T> uncle, Node<T> grandParent) {
        uncle.flipColor();
        parent.flipColor();
        grandParent.flipColor();
        recolorAndRotate(grandParent);
    }

    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        node.setLeftChild(leftNode.getRightChild());
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        leftNode.setRightChild(node);
        leftNode.setParent(node.getParent());
        updateChildrenOfParentNode(node, leftNode);
        node.setParent(leftNode);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        node.setRightChild(rightNode.getLeftChild());
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        rightNode.setLeftChild(node);
        rightNode.setParent(node.getParent());
        updateChildrenOfParentNode(node, rightNode);
        node.setParent(rightNode);
    }

    private void updateChildrenOfParentNode(Node<T> node, Node<T> tempNode) {
        if (node.getParent() == null) {
            root = tempNode;
        } else if (node.isLeftChild()) {
            node.getParent().setLeftChild(tempNode);
        } else {
            node.getParent().setRightChild(tempNode);
        }
    }

    @Override
    public void find(T data) {
        find(root, data);
    }

    private void find(Node<T> node, T value) {
        while (node != null && node.getData() != value) {
            if (value.compareTo(node.getData()) < 0) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
    
        if (node == null) {
            System.out.println("RBT: No se ha encontrado " + value);
        }
    }

    @Override
    public T getMax() {
        if (isEmpty()) {
            return null;
        }

        Node<T> currentNode = root;
        while (currentNode.getRightChild() != null) {
            currentNode = currentNode.getRightChild();
        }

        return currentNode.getData();
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }

        Node<T> currentNode = root;
        while (currentNode.getLeftChild() != null) {
            currentNode = currentNode.getLeftChild();
        }

        return currentNode.getData();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
    
}
