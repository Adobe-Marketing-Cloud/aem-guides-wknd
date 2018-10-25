package com.adobe.aem.guides.wknd.core.components;

import java.util.List;

public interface Byline {
	 /***
     * @return a string to display as the name.
     */
    String getName();
 
    /***
     * Occupations are to be sorted alphabetically in a descending order.
     * 
     * @return a collection of strings that represent the occupations.
     */
    List<String> getOccupations();
 
    /***
     * @return a boolean if the component has content to display.
     */
    boolean isEmpty();
}
