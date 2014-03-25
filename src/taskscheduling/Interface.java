/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskscheduling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * User interface / execution for the Task Scheduling package
 * @author Greg Volpe
 */
public class Interface 
{
    ArrayList<Task> mTask;
    ArrayList<Task> mTaskCopy;
    int mMode;
    int mAlgorithm;
    int taskNum;
    int[] burst;
    
    /**
     * Public constructor for the Interface class.
     * \nUser will be prompted with questions about operating parameters
     */
    public Interface()
    {
        navigation();

    }
    /**
     * This will create a new task list based on the burst data that is already in the system
     * @return ArrayList of Tasks
     */
    private ArrayList<Task> generateTask()
    {
        ArrayList<Task> taskCopy = new ArrayList<>();
        try
        {
            File file = new File("results.txt");
            Writer writer = new BufferedWriter(new FileWriter(file,true));
        
            mTask = new ArrayList<>();
            int temp;
            writer.write("\n"+getTimestamp());
            writer.write("\nAutomatically generated tasks:");
            for(int i=0; i<taskNum; i++)
            {
                Task tempTask;
                temp = burst[i];
                tempTask = new Task(i,temp);
                mTask.add(tempTask);
                taskCopy.add(tempTask);
                writer.write("\nP" +i + "  Burst: " + temp);
            }
            writer.write("\n");
            writer.close();
        }
        catch(Exception e)
            {
                System.out.println("Error Writing File");
            }
        return taskCopy;

    }
    /**
     * Prompts user to set the desired Algorithm
     */
    private void setAlgorithm()
    {
        int algorithm;
            do
            {
            algorithm = inputNum("Which Algorithm would you like to enter?\n1 - FCFS\n2 - RR");
            }
        while ( algorithm <1 || algorithm >2);
            mAlgorithm = algorithm;
    }
    
    /**
     * Executes single algorithm mode
     */
    private void execute()
    {
        generateTask();

            if(mAlgorithm ==1)
            {
                FCFS_Activity fcfs = new FCFS_Activity(mTask);
                fcfs.execute();

            }
            if(mAlgorithm ==2)
            {
                int CPU_TIME;
                do
                {
                    CPU_TIME = inputNum("Enter CPU time 1-10");
                
                }while ( CPU_TIME <1 || CPU_TIME >10);
                RR_Activity RR = new RR_Activity(mTask, CPU_TIME);
                RR.execute();
                
            }//END IF
        
    }//END METHOD
    
    /**
     * Input the number of tasks to be generated
     */
    private void taskInput()
    {
        taskNum =0;
        do
        {
            taskNum = inputNum("Enter number of tasks");

        }while ( taskNum <=0);
    }//end method
    
    /**
     * Generate new burst rates for tasks
     */
    private void generateBurst()
    {
        burst = new int[taskNum];
        for(int i =0; i< taskNum; i++)
        {
            burst[i]= 1+ (int) (Math.random() * (100-1));
        }
    }
    /**
     * Prompts user to input a new number, checks for incorrect characters
     * @param words String output to command line
     * @return number entered
     */
    private int inputNum(String words)
    {
        Scanner input = new Scanner(System.in);
        int num;
        System.out.println(words);
            while (!input.hasNextInt())
            {
                System.out.println("Enter a valid option");
                input.next();
            }
            num = input.nextInt();
        return num;
    }
    /**
     * Returns timestamp
     * @return timestamp
     */
    private Timestamp getTimestamp()
    {
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        return currentTimestamp;
    }
    
    /**
     * Prints data in results.txt to the command line
     */
    private void printFile()
    {
        try
        {
            String line = null;
            BufferedReader in = new BufferedReader(new FileReader("results.txt"));
            while((line = in.readLine()) != null)
            {
            System.out.println(line);
            }

            in.close();
        }
        catch(Exception e)
        {
            System.out.println("File input error");
        }
    }
    /**
     * Clears the content of results.txt
     */
    private void clearFile()
    {
        try 
        {
            File file = new File("results.txt");
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write("\n");
            
        }
        catch(Exception e)
        {
            System.out.println("File Write Error");
        }
    }
    /**
     * Starts the standard user interface 
     */
    private void navigation()
    {
        //Scanner in = new Scanner(System.in);
        int input=0;
        int input2 = 0;

        if(burst == null)
        {
            taskNum = 10+ (int)(Math.random() * ((20-10)+1));
            generateBurst();
            generateTask();
        }
        while (input !=5)
        {
            input = 0;
            input = inputNum("Please select a value:"
                    +"\n1 - Single Algorithm"
                    +"\n2 - Algorithm Compare"
                    +"\n3 - Generate New Tasks"
                    +"\n4 - Help"
                    +"\n5 - EXIT");
            switch(input)
            {
                case 1:
                    do
                    {
                        setAlgorithm();
                        execute();
                        printFile();
                        input2 = inputNum("Would you like to run again?"
                                + "\n1 - yes"
                                + "\n2 - no");
                    }while(input2 !=2);
                    break;
                case 2:
                    executeCompare();
                    printFile();
                    break;
                case 3:
                    generateBurst();
                    generateTask();
                    break;
                case 4:
                    displayHelp();
                    break;
                case 5: System.exit(0);
                    break;
                default: System.out.println("Invalid selection"); break;
            }
        }
    }
    
    /**
     * Execute algorithm compare mode
     */
    private void executeCompare()
    {
        String output = "Enter number of sets";
        int number = inputNum(output);
        for(int i=0;i<number;i++)
        {
            if(burst == null)
            {
                taskNum = 10+ (int)(Math.random() * ((20-10)+1));
                generateBurst();
                
            }
            generateTask();
            FCFS_Activity fcfs = new FCFS_Activity(mTask);
            fcfs.execute();
            generateTask();
            RR_Activity RR = new RR_Activity(mTask, 1);
            RR.execute();
            generateBurst();

        }

    }
    private void displayHelp()
    {
        System.out.println("\n");
        String help = 
                  "*******************Welcome to Help***************************"
                + "\n  This Application will replicate two types of task scheduling"
                + "\n  performed by CPUs."
                + "\n  1. Round Robin"
                + "\n  2. First Come First Serve"
                + "\n  This program operates under the assumption that all tasks"
                + "\n  arrived at time = zero.  See the following options for more"
                + "\n  information."
                + "\n"
                + "\nOption 1: Single Algorithm"
                + "\n"
                + "\n----------------Single Algorithm---------------------------"
                + "\n--Application will automatically generate a number of tasks"
                + "\n  between 10-20 and fill with random CPU burst times between"
                + "\n  1-100.  You will then be prompted to select an algorithm"
                + "\n  to use for scheduling.  Results will be printed on screen"
                + "\n  providing generating tasks with burst times, and the average"
                + "\n  waiting time for the entire process."
                + "\n"
                + "\nOption 2: Algorithm Compare"
                + "\n"
                + "\n----------------Algorithm Compare--------------------------"
                + "\n--Application will prompt you for the number of sets to run"
                + "\n  through both algorithms. For example, if you chose 2 sets"
                + "\n  Set 1 will be run through both algorithms, then set 2 will "
                + "\n  be run through both algorithms.  Results will be generated"
                + "\n  and printed to the file results.txt as well as output to the"
                + "\n  screen when finished."
                + "\n"
                + "\nOption 3: Generate New Tasks"
                + "\n"
                + "\n--Application will generate a new set of tasks to process."
                + "\n  After selecting this option you will be brought back to"
                + "\n  the main menu to select which mode to operate in."
                + "\n"
                + "\nOption 4: Help"
                + "\n"
                + "\n--You should realize by now what this option does..."
                + "\n"
                + "\nOption 5: Exit"
                + "\n"
                + "\n--Exit the application."
                + "\n"
                + "\n********************End of Help***************************";
        System.out.println(help);
                
    }

}
