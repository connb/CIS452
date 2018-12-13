#include <sys/resource.h> 
#include <sys/time.h> 
#include <unistd.h> 
#include <stdio.h> 
  
 int main () 
 { 
   struct rlimit rl; 
  
   getrlimit (RLIMIT_FSIZE, &rl); 
  
   printf("\n Hard Limit is : %lld\n", (long long int)rl.rlim_max);
   printf("\n Soft Limit is : %lld\n", (long long int)rl.rlim_cur);  
  
   return 0; 
 }
