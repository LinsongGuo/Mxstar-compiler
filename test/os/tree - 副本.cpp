#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
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

void insert(Node *node, int value) {
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
	cout << node->value;
	print(node->left);
	print(node->right);
}

int main() {
	insert(root, 5);
	insert(root, 3);
	insert(root, 7);
	pthread_t t1, t2;
	pthread_create(&t1, NULL, f1, NULL);
	pthread_create(&t2, NULL, f2, NULL);
	pthread_create(&t1, NULL);
	pthread_create(&t2, NULL);
	print(root);
	return 0;
}
