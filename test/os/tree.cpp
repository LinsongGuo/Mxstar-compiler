#include<stdio.h>
#include<stdlib.h>
#include<thread>
#include <iostream>
using namespace std;

struct Node {
	int value;
	Node *left, *right;
	Node(int _value) {
		value = _value;
	}
};
Node *root;

void insert(Node *&node, int value) {
	if (node == NULL) {
		node = new Node(value);
		return;
	}
	if (value <= node->value) { 
		if (node->left == NULL) { 
			node->left = new Node(value); 
		} 
		else { 
			insert(node->left, value); 
		} 
	} 
	else { 
		if (node->right == NULL) { 
			node->right = new Node(value); 
		} 
		else { 
			insert(node->right, value); 
		} 
	} 
}

void f1() {
	insert(root, 0);
	insert(root, 2);
}

void f2() {
	insert(root, 1);
	insert(root, 6);
}

void print(Node *node) {
	if (node == NULL) return;
	printf("%d  left:%d  right:%d\n", node->value, 
		node->left == NULL ? -1 : node->left->value, 
		node->right == NULL ? -1 : node->right->value);
	print(node->left);
	print(node->right);
}

int main() {
	insert(root, 5);
	insert(root, 3);
	insert(root, 7);
	
	std::thread t2(f2);
	std::thread t1(f1);
	t2.join();
	t1.join();
	
	print(root);

	return 0;
}
