package gh2;

import deque.Deque;
import deque.ArrayDeque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;
    private int capacity;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        capacity = (int) Math.round(SR / frequency);
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        double r = Math.random() - 0.5;
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double removedFirst = buffer.removeFirst();
        double newDouble = ((removedFirst + buffer.get(0)) / 2) * DECAY;
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
