/**
 * Copyright 2018 bejson.com
 */
package org.linlinjava.litemall.core.deppon.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.linlinjava.litemall.core.express.dao.Traces;

import java.util.List;

/**
 * Auto-generated: 2018-07-19 22:27:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DepponTraceInfo {

    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("result")
    private String result;
    @JsonProperty("trace_list")
    private List<Traces> traces;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Traces> getTraces() {
        return traces;
    }

    public void setTraces(List<Traces> traces) {
        this.traces = traces;
    }
}