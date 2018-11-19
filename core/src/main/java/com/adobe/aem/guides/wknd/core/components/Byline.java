package com.adobe.aem.guides.wknd.core.components;

import java.util.List;

/** 
 * Represents the Byline AEM Component for the WKND Site project.
 **/
public interface Byline {
    /***
     * @return a string to display as the name.
     */
    String getName();
 
    /***
     * Occupations are to be sorted alphabetically in a descending order.
     * 
     * @return a list of occupations.
     */
    List<String> getOccupations();
 
    /***
     * @return a boolean if the component has content to display.
     */
    boolean isEmpty();
}