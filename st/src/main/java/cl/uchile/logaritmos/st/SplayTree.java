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

package cl.uchile.logaritmos.st;

public class SplayTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public Tree<T> insert(T data) {
        root = insert(root, new Node<>(data));
        return this;
    }

    private Node<T> insert(Node<T> node, Node<T> newNode) {
        if (node == null) {
            return newNode;
        }

        Node<T> currentNode = root;
        Node<T> parentNode;

        while (true) {
            parentNode = currentNode;

            if (newNode.getData().compareTo(currentNode.getData()) < 0) {
                currentNode = currentNode.getLeftChild();
                if (currentNode == null) {
                    parentNode.setLeftChild(newNode);
                    newNode.setParent(parentNode);
                    break;
                }
            } else if (newNode.getData().compareTo(currentNode.getData()) > 0) {
                currentNode = currentNode.getRightChild();
                if (currentNode == null) {
                    parentNode.setRightChild(newNode);
                    newNode.setParent(parentNode);
                    break;
                }
            } else {
                // Node with the same data already exists, handle accordingly
                break;
            }
        }

        return node;
    }

    @Override
    public void find(T data) {
        Node<T> node = root;
        while (node != null) {
            if (node.getData().compareTo(data) == 0) {
                splay(node);
                return;
            }
            node = data.compareTo(node.getData()) < 0 ? node.getLeftChild() : node.getRightChild();
        }
        System.out.println("ST: No se ha encontrado "+ data);
    }

    private void splay(Node<T> node) {
        while (node.getParent() != null) {
            if (node.getParent().getParent() == null) {
                if (node.isLeftChild()) {
                    rotateRight(node.getParent());
                } else {
                    rotateLeft(node.getParent());
                }
            } else if (node.isLeftChild() && node.getParent().isLeftChild()) {
                rotateRight(node.getParent().getParent());
                rotateRight(node.getParent());
            } else if (node.isRightChild() && node.getParent().isRightChild()) {
                rotateLeft(node.getParent().getParent());
                rotateLeft(node.getParent());
            } else if (node.isLeftChild() && node.getParent().isRightChild()) {
                rotateRight(node.getParent());
                rotateLeft(node.getParent());
            } else {
                rotateLeft(node.getParent());
                rotateRight(node.getParent());
            }
        }
    }

    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        node.setLeftChild(leftNode != null ? leftNode.getRightChild() : null);
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        updateChildrenOfParentNode(node, leftNode);
        if(leftNode != null){
            leftNode.setParent(node.getParent());
            leftNode.setRightChild(node);
        }
        node.setParent(leftNode);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        node.setRightChild(rightNode != null ? rightNode.getLeftChild() : null);
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        updateChildrenOfParentNode(node, rightNode);
        if(rightNode != null){
            rightNode.setParent(node.getParent());
            rightNode.setLeftChild(node);
        }
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