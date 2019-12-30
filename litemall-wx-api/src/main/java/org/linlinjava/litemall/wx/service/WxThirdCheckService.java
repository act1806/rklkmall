package org.linlinjava.litemall.wx.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallThirdCheck;
import org.linlinjava.litemall.db.service.LitemallThirdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class WxThirdCheckService {
    private final Log logger = LogFactory.getLog(WxThirdCheckService.class);

    @Autowired
    private LitemallThirdCheckService thirdCheckService;


    /**
     * 提交送检单
     */
    @Transactional
    public Object submit(String body) {

        if (body == null) {
            return ResponseUtil.badArgument();
        }

        String clinical = JacksonUtil.parseString(body, "clinical");
        String cls = JacksonUtil.parseString(body, "cls");
        String doctorName = JacksonUtil.parseString(body, "doctorName");
        String email = JacksonUtil.parseString(body, "email");
        String examine = JacksonUtil.parseString(body, "examine");
        String exampleProject = JacksonUtil.parseString(body, "exampleProject");
        String gender = JacksonUtil.parseString(body, "gender");
        String history = JacksonUtil.parseString(body, "history");
        String hospitalName = JacksonUtil.parseString(body, "hospitalName");
        String isStered = JacksonUtil.parseString(body, "isStered");
        String isDiolame = JacksonUtil.parseString(body, "isDiolame");
        String isRupture = JacksonUtil.parseString(body, "isRupture");
        String isStick = JacksonUtil.parseString(body, "isStick");
        String petName = JacksonUtil.parseString(body, "petName");
        String phone = JacksonUtil.parseString(body, "phone");
        String sampleAddr = JacksonUtil.parseString(body, "sampleAddr");
        String sampleArea = JacksonUtil.parseString(body, "sampleArea");
        String sampleCondition = JacksonUtil.parseString(body, "sampleCondition");
        String sampleDateStr = JacksonUtil.parseString(body, "sampleDate");
        List<String> sampleFeatureList = JacksonUtil.parseStringList(body, "sampleFeature");
        List<String> sampleProjectList = JacksonUtil.parseStringList(body, "sampleProject");
        String sampleSize = JacksonUtil.parseString(body, "sampleSize");
        String texture = JacksonUtil.parseString(body, "texture");
        String expressCarrier = JacksonUtil.parseString(body, "expressCarrier");
        String expressNo = JacksonUtil.parseString(body, "expressNo");
        List<String> expressUrlList = JacksonUtil.parseStringList(body, "expressUrl");





        Integer id = null;
        LitemallThirdCheck thirdCheck = null;
        //送检单
        thirdCheck = new LitemallThirdCheck();
        thirdCheck.setClinical(clinical);
        thirdCheck.setCls(cls);
        thirdCheck.setDoctorName(doctorName);
        thirdCheck.setEmail(email);
        thirdCheck.setExamine(examine);
        thirdCheck.setExampleProject(exampleProject);
        thirdCheck.setGender(gender);
        thirdCheck.setHistory(history);
        thirdCheck.setHospitalName(hospitalName);
        thirdCheck.setIsStered(isStered);
        thirdCheck.setIsDiolame(isDiolame);
        thirdCheck.setIsRupture(isRupture);
        thirdCheck.setIsStick(isStick);
        thirdCheck.setPetName(petName);
        thirdCheck.setPhone(phone);
        thirdCheck.setSampleAddr(sampleAddr);
        thirdCheck.setSampleArea(sampleArea);
        if(!StringUtils.isEmpty(sampleCondition)){
            thirdCheck.setSampleCondition(sampleCondition);
        }

        if(!StringUtils.isEmpty(sampleDateStr)){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            try {
                LocalDateTime ldt = df.parse(sampleDateStr).toInstant()
                        .atZone( ZoneId.systemDefault() )
                        .toLocalDateTime();
                thirdCheck.setSampleDate(ldt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //thirdCheck.setSampleDate(sampleDate);
        if(sampleFeatureList != null) {
            String sampleFeature = listToString(sampleFeatureList, ',');
            thirdCheck.setSampleFeature(sampleFeature);
        }
        if(sampleProjectList != null) {
            String sampleProject = listToString(sampleProjectList, ',');
            thirdCheck.setSampleProject(sampleProject);
        }


        thirdCheck.setSampleSize(sampleSize);
        thirdCheck.setTexture(texture);
        thirdCheck.setExpressCarrier(expressCarrier);
        thirdCheck.setExpressNo(expressNo);

        if(expressUrlList != null) {
            StringBuilder sb = new StringBuilder();
            int offset = expressUrlList.size() - 1;
            for( int i = 0; i < offset; i++ )
            {
                sb.append(expressUrlList.get(i)).append(",");
            }
            sb.append(expressUrlList.get(offset));

            thirdCheck.setExpressUrl(sb.toString());
        }


        // 添加送检单
        thirdCheckService.add(thirdCheck);




        Map<String, Object> data = new HashMap<>();
        data.put("id", "");
        return ResponseUtil.ok(data);

    }

    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
