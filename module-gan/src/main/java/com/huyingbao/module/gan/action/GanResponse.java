package com.huyingbao.module.gan.action;

import java.util.ArrayList;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class GanResponse<T> {

    private boolean error;
    private ArrayList<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }
}
