#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* __malloc(int n) {
	return (char*) malloc(n);
}

void __print(char *str) {
	printf("%s", str);
}

void __println(char *str) {
	printf("%s\n", str);
}

void __printInt(int n) {
	printf("%d", n);
}

void __printlnInt(int n) {
	printf("%d\n", n);
}

char* __getString() {
	char* buffer = malloc(sizeof(char) * 512);
    scanf("%s", buffer);
    return buffer;
} 

int __getInt() {
	int x;
	scanf("%d", &x);
	return x;
}

char* __toString(int i) {
	int flag = 0, len = 0, digits[10];
	if (i == 0) digits[len++] = 0;
	if (i < 0) {
		flag = 1;
		i = -i;
	}
	for(; i > 0; i /= 10) { 
		digits[len++] = i % 10;
	}	

	char* res = (char*) malloc(sizeof(char) * (flag + len + 1));
	int now = 0;
	if(flag) res[now++] = '-';
	while(len > 0) {
		res[now++] = '0' + digits[--len];
	}
	res[now++] = '\0';
	return res;
}

int __stringLength(char *str) {
	int len = 0;
	while (str[len] != '\0')
		len++;
	return len;
}

char* __stringSubstring(char *str, int l, int r) {
	int len = r - l, i = 0;
	char* res = (char*) malloc(sizeof(char) * (len + 1));
	for (i = 0; i < len; ++i) {
		res[i] = str[i + l];
	} 
	res[len] = '\0';
	return res;
}

int __stringParseInt(char* str) {
	int res;
    sscanf(str, "%d", &res);
    return res;
}

int __stringOrd(char* str, int pos) {
	return str[pos];
}

int __arraySize(char* array){
   return *(((int*)array)-1);
}


char* __stringAdd(char* str1, char* str2) {
	int len1, len2;
	for (len1 = 0; str1[len1] != '\0'; ++len1);
	for (len2 = 0; str2[len2] != '\0'; ++len2);

	char* res = (char* ) malloc(sizeof(char) * (len1 + len2 + 1));
	int i, j;
	for (i = 0; i < len1; ++i) res[i] = str1[i];
	for (j = 0; j < len2; ++j) res[j + len1] = str2[j];
	res[len1 + len2] = '\0';
	
	return res;
}

/*
char* __stringAdd(char* str1,char* str2){
    int len1 = strlen(str1), len2 = strlen(str2);
    char* res = (char*) malloc(len1 + len2 + 1);
    strcpy(res, str1);
    strcat(res, str2);
    return res;
}
*/

char __stringEqual(char* str1, char *str2) {
	return strcmp(str1, str2) == 0;
}

char __stringNotEqual(char* str1, char* str2) {
	return strcmp(str1, str2) != 0;
}

char __stringLess(char *str1, char* str2) {
	return strcmp(str1, str2) < 0;
}

char __stringLessEqual(char* str1, char* str2) {
	return strcmp(str1, str2) <= 0;
}

char __stringGreater(char* str1, char* str2) {
	return strcmp(str1, str2) > 0;
}

char __stringGreaterEqual(char* str1, char* str2) {
	return strcmp(str1, str2) >= 0;
}