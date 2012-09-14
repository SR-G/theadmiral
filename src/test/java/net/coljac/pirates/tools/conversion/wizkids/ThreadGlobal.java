package net.coljac.pirates.tools.conversion.wizkids;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 * 
 * @param <E>
 *            the element type
 */
public class ThreadGlobal<E> {

    /** The obj. */
    private E obj;

    /**
     * Gets the.
     * 
     * @return the e
     */
    public E get() {
        return obj;
    }

    /**
     * Sets the.
     * 
     * @param obj
     *            the obj
     */
    public void set(E obj) {
        this.obj = obj;
    }

}
