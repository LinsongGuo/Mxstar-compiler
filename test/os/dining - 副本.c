#include<stdio.h>
#include<stdlib.h> 
#include<time.h>
#include<semaphore.h>
#include<pthread.h>
 
#define N 5
#define THINKING 0
#define HUNGRY 1
#define EATING 2
#define LEFT (ph_num+4)%N
#define RIGHT (ph_num+1)%N

// add anything you want 
typedef int semaphore;
semaphore mutex = 1;
semaphore cond[N];

void * philospher(void *num);
void take_fork(int);
void put_fork(int);
void test(int);
 
int state[N];
int phil_num[N]={0,1,2,3,4};


int main()
{
    int i;
    pthread_t thread_id[N];
    
    // some codes goes here
        
    srand((unsigned)time(NULL)); 
    for(i=0;i<N;i++)
    {
        pthread_create(&thread_id[i], NULL, philospher, &phil_num[i]);
        printf("Philosopher %d is thinking\n", i+1);
    }
    for(i=0;i<N;i++)
        pthread_join(thread_id[i],NULL);
    return 0;
}
 
void *philospher(void *num)
{
	while(1)
    {
        int *i = num;
        sleep(rand()%5);
        take_fork(*i);
        sleep(rand()%5);
        put_fork(*i);
    }
    
}
 
void take_fork(int ph_num)
{
    state[ph_num] = HUNGRY;
    printf("Philosopher %d is Hungry\n", ph_num+1);
    
    // some codes goes here
    down(&mutex);
    test(ph_num);
    up(&mutex);
    down(&cond[ph_num]);

    sleep(1);
}
 
void test(int ph_num)
{
    if (state[ph_num] == HUNGRY && state[LEFT] != EATING && state[RIGHT] != EATING) {
        state[ph_num] = EATING;
        up(&cond[ph_num]);
    }
}
 
void put_fork(int ph_num)
{
    down(&mutex);
    state[ph_num] = THINKING;
    printf("Philosopher %d putting fork %d and %d down\n",ph_num+1,LEFT+1,ph_num+1);
    printf("Philosopher %d is thinking\n",ph_num+1);
    test(LEFT);
    test(RIGHT);
    up(&mutex);
    // some codes goes here
}
