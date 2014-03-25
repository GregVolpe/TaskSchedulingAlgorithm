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
import java.util.Calendar;

/**
 *Simple class to reproduce First Come First Serve (FCFS) CPU scheduling
 * Operates under the assumption that all tasks arrive at time mark zero
 * @author HEX
 */
public class FCFS_Activity 
{
    ArrayList<Task> mTasks;
    int waitingTime;
    int time;
    
    /**
     * FCFS_Activity constructor
     * @param tasks ArrayList of Tasks that will be scheduled
     */
    public FCFS_Activity(ArrayList<Task> tasks)
    {
        mTasks = new ArrayList(tasks);
        
    }
    
    /**
     *Executes the FCFS scheduling program
     */
    public void execute()
    {
        
        try
        {
            File file = new File("results.txt");
            Writer writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("\n"+getTimestamp());
            int numTasks = mTasks.size();
            waitingTime = 0;
            time = 0;
            writer.write("\nFCFS Algorithm:\n");
            while (! mTasks.isEmpty())
            {
                Task temp = mTasks.remove(0);
                writer.write("\nTask " + temp.mIndex + " Wait Time: " + waitingTime);
                waitingTime += temp.mBurst;
            }
            writer.write("\nAverage waiting time: "+ ((waitingTime)/numTasks));
            writer.close();
        }
        catch(Exception e)
            {
                System.out.println("Error Writing File");
            }
    }
    
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
