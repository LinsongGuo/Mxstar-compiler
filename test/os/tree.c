#include<stdio.h>
#include<stdlib.h> 
#include<time.h>
#include<semaphore.h>
#include<pthread.h>

struct Node {
	int value;
	Node *left, *right;
	Node(int _value) {
		value = _value;
	}
}root;

void insert(Node *node, int value) {
	if (value <= node->value) { 
		if (node->left == null) { 
			node->left = new Node(value); 
		} 
		else { 
			insert(node->left, value); 
		} 
	} 
	else { 
		if (node->right == null) { 
			node->right = new Node(value); 
		} 
		else { 
			insert(node->right, value); 
		} 
	} 
}

int main() {
	insert(root, )
	pthread_t t1, t2;
	pthread_create(&thread_id[i], NULL, f1, NULL);

    
}
