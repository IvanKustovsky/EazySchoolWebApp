package com.example.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EazySchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> easySchoolMap = new HashMap<>();
        easySchoolMap.put("App Name", "EazySchool");
        easySchoolMap.put("App Description", "Eazy School Web Application for Students and Admin");
        easySchoolMap.put("App Version", "1.0.0");
        easySchoolMap.put("Contact Email", "info@eazyschool.com");
        easySchoolMap.put("Contact Mobile", "+1(21) 785 8123");
        builder.withDetail("eazyschool-info", easySchoolMap);
    }
}
