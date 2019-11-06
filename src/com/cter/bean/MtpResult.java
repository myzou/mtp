package com.cter.bean;

import java.util.ArrayList;
import java.util.List;

public class MtpResult {
    private String ticketName;
    private String tense;
    private List<ResultList> results;

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public List<ResultList> getResults() {
        return results;
    }

    public void setResults(List<ResultList> results) {
        this.results = results;
    }

}

