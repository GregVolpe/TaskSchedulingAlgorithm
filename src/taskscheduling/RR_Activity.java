/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskscheduling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Simple class to reproduce Round Robin style CPU scheduling
 * Operates under the assumption that all tasks arrive at time mark zero
 * @author HEX
 */
public class RR_Activity 
{
    ArrayList<Task> mTasks;
    final int CPU_TIME;
    private int count,averageWait;
    boolean[] jobStart;
    /**
     * RR_Activity constructor
     * @param tasks The ArrayList of Tasks to be scheduled
     * @param CPU Amount of CPU time to be allocated to each execution cycle
     */
    public RR_Activity(ArrayList<Task> tasks, int CPU)
    {
        mTasks = new ArrayList(tasks);
        CPU_TIME = CPU;
        count = 0;
        averageWait=0;
        jobStart = new boolean[mTasks.size()];
        Arrays.fill(jobStart, false);
    }
    /**
     * Executes the Round Robin scheduling
     */
    public void execute()
    {
        int numTasks = mTasks.size();
        int timeMark = 0;
        double averageTurnaround=0;
        
        try
        {
            File file = new File("results.txt");
            Writer writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("\n"+getTimestamp());
        
            writer.write("\nRound Robin Algorithm:\n\n");
            while (!mTasks.isEmpty())
            {
                //System.out.println(count);
                Task temp = mTasks.remove(0);
                if(jobStart[temp.mIndex] == false)
                {
                    averageWait+=timeMark;
                    jobStart[temp.mIndex]=true;
                    writer.write("P"+temp.mIndex+" Wait Time: "+timeMark+"\n");
                }
                if (temp.mBurst > CPU_TIME)
                {
                    timeMark += CPU_TIME;
                    temp.mBurst -= CPU_TIME; 
                    mTasks.add(temp);
                
                }//end if

                else
                {
                    timeMark += temp.mBurst;
                    averageTurnaround += timeMark;
                    temp.mBurst = 0;
                } //end else
                
                

                //writer.write("Burst left "+ temp.mBurst + "\n");
            }//end while
            
            averageTurnaround /=numTasks;
            averageWait/= numTasks;
            writer.write("\nAverage Turn Around: " +averageTurnaround);
            writer.write("\n");
            writer.write("Average Wait Time: " +averageWait + "\n");
            writer.close();
            }
            catch(Exception e)
            {
                System.out.println("File writing error");
            }
        }//end execute
    
    /**
     * Provides a timestamp
     * @return timestamp
     */
    private Timestamp getTimestamp()
    {
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        return currentTimestamp;
    }
    
}
