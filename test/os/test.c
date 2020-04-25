#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/*
int main(void){
	char** argv = (char**) malloc(3*sizeof(char*)); 
	printf("Let’s count the stars! \n"); 
	argv[0] = "/bin/ls"; 
	argv[1] = "."; 
	argv[2] = NULL;
	for (int i = 0; i < 10; ++i) {
		printf("%d..\n", i);
		if (i == 5)
			execv("/bin/ls", argv);
	}
	printf("Oh, there are 10 stars! \n");
	return 0; 
}

*/

int main() {
	if (fork() || fork())
		fork();
	printf("1");
	return 0;
}

