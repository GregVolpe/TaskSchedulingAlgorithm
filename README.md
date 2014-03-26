TaskSchedulingAlgorithm
=======================
**Task:**
In this exercise you will be asked to design and implement a program, which allow you to evaluate and compare different scheduling algorithms. List of implemented algorithms for evaluation should include FCFS algorithm and at least one from the following:
-	SJF – preemptive and non-preemptive
-	Priority scheduling – preemptive and non-preemptive
-	RR 
-	Multilevel feedback queue
Your program should be able to work in two different modes: “Single algorithm performance evaluation” and “Algorithm comparing”

When running in “Single algorithm performance evaluation” mode the program should perform the following:
-	Generate a set of data representing set of processes (min 10 and max 20) to be executed in the “ready” queue.  You have to create an appropriate data structure to store data associated with each process (Process name, Burst time, Priority, Arrival time etc) and to implement “ready” queue. Use the random number generator for test data generation. 
-	Prompt the user to select scheduling algorithm from the list and to enter specific parameters for selected algorithm if necessary.
-	Calculate average waiting time using selected algorithm.
-	Display test data, name of the selected algorithm and calculated average time
-	Prompt the user to end the program or to select different algorithm for evaluation using the same set of data.  
When running in “Algorithm comparing” mode the program should be able to calculate the average waiting times for all implemented algorithm using several sets of generated data (number of sets should be given by the user), present the results on the screen, and store the results in a file for analysis, evaluation of the algorithm performance and trend analyses.
