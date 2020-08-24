
package com.luis.myapp.utilities;

import java.util.List;


public class BEAN_PAGINATION {

    private Integer COUNT_FILTER;
    private List<?> LIST;

    public BEAN_PAGINATION() {
    }

    public Integer getCOUNT_FILTER() {
        return COUNT_FILTER;
    }

    public void setCOUNT_FILTER(Integer COUNT_FILTER) {
        this.COUNT_FILTER = COUNT_FILTER;
    }

    public List<?> getLIST() {
        return LIST;
    }

    public void setLIST(List<?> LIST) {
        this.LIST = LIST;
    }
    
}
