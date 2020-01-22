package org.linlinjava.litemall.core.deppon;

import com.deppon.dop.module.sdk.shared.domain.query.QueryOrderInfoRequest;
import com.deppon.dop.module.sdk.shared.domain.result.ResultDO;
import com.deppon.dop.module.sdk.shared.util.FastJsonUtil;
import com.deppon.dop.module.sdk.shared.util.HttpUtils;
import com.deppon.dop.module.sdk.shared.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.deppon.dao.DepponTraceInfo;
import org.linlinjava.litemall.core.deppon.dao.TraceList;
import org.linlinjava.litemall.core.express.dao.Traces;
import org.linlinjava.litemall.core.express.dao.ExpressInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepponService {
    private final Log logger = LogFactory.getLog(DepponService.class);

    public void queryOrder(){
        //订单内容 json字符串，SDK提供FastJsonUtil转Json
        QueryOrderInfoRequest qoir = new QueryOrderInfoRequest();
        qoir.setLogisticCompanyID("EWBBJRKLKKJYXG");
        qoir.setLogisticID("RITZ33343111111113");
        String params= FastJsonUtil.toJSONString(qoir);
        //companyCode与appkey为双方约定
        String companyCode="EWBBJRKLKKJYXG";
        String appkey="62943e9aed8fd0c49317e86e022c3fb0";
        //时间戳 SDK提供SecurityUtil获取时间戳
        String timestamp=Long.toString(System.currentTimeMillis());
        //摘要 SDK提供SecurityUtil生成摘要
        String digest= SecurityUtil.getStandardDigest(params+appkey+timestamp);
        //post请求参数
        NameValuePair[] data = new NameValuePair[4];
        data[0] = new NameValuePair("companyCode", companyCode);
        data[1] = new NameValuePair("digest", digest);
        data[2] = new NameValuePair("timestamp", timestamp);
        data[3] = new NameValuePair("params", params);
        System.out.println(data[3]);
        //请求url
        String url="http://dpsanbox.deppon.com/sandbox-web/standard-order/queryOrder.action";
        ResultDO<String> response=null;
        //返回结果
        response= HttpUtils.sendRequest(url, data, "UTF-8", 5000);
        System.out.println(response.getModel());
    }

    public String newTraceQuery(String mailNo){
        //订单内容 json字符串，SDK提供FastJsonUtil转Json
        String params= "{\"mailNo\":" + mailNo + "}";
        //companyCode与appkey为双方约定
        String companyCode="EWBBJRKLKKJYXG";
        String appkey="62943e9aed8fd0c49317e86e022c3fb0";
        //时间戳 SDK提供SecurityUtil获取时间戳
        String timestamp=Long.toString(System.currentTimeMillis());
        //摘要 SDK提供SecurityUtil生成摘要
        String digest= SecurityUtil.getStandardDigest(params+appkey+timestamp);
        //post请求参数
        NameValuePair[] data = new NameValuePair[4];
        data[0] = new NameValuePair("companyCode", companyCode);
        data[1] = new NameValuePair("digest", digest);
        data[2] = new NameValuePair("timestamp", timestamp);
        data[3] = new NameValuePair("params", params);
        //请求url
        String url="http://dpsanbox.deppon.com/sandbox-web/standard-order/newTraceQuery.action";
        ResultDO<String> response=null;
        //返回结果
        response= HttpUtils.sendRequest(url, data, "UTF-8", 5000);
        return response.getModel();
    }

    /**
     * 获取物流信息
     *
     * @param expNo
     * @return
     */
    public ExpressInfo getDepponExpressInfo(String expNo) {
        try {
            String result = newTraceQuery(expNo);
            ObjectMapper objMap = new ObjectMapper();
            DepponTraceInfo dti = objMap.readValue(result, DepponTraceInfo.class);
            List<Traces> tls = dti.getTraces();
            List<TraceList> traces = new ArrayList<>();
            for(Traces tl : tls){
                TraceList trace = new TraceList();
                trace.setDescription(tl.getAcceptStation());
                trace.setTime(tl.getAcceptTime());
                traces.add(trace);
            }

            ExpressInfo ei = new ExpressInfo();
            ei.setShipperName("德邦快递");
            ei.setSuccess(Boolean.parseBoolean(dti.getResult()));
            ei.setTraces(traces);
            return ei;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
