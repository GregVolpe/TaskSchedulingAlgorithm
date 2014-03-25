/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskscheduling;

/**
 * Simple class to represent a Task needed to be scheduled
 * @author HEX
 */
public class Task 
{
    public int mBurst, mIndex;
    /**
     * Constructor for the Task object
     * @param index index of the task
     * @param burst Task Burst time
     */
    public Task(int index, int burst)
    {
        mIndex = index;
        mBurst = burst;
        
    }
}
