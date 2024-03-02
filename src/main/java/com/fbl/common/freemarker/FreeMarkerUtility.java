package com.fbl.common.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Free Marker Utility
 * 
 * @author Sam Butler
 * @since Mar 02, 2024
 */
@Component
public class FreeMarkerUtility {

    @Autowired
    private Configuration freeMarkerConfig;

    /**
     * Generate string message from template
     * 
     * @param templateFilename    A file template in the resources folder
     * @param objectToBeProcessed whatever object should be put into the template
     * @return String of the generated content
     */
    public <T> String generateMessage(String templateFilename, T objectToBeProcessed)
            throws Exception {
        freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/");
        Template template = freeMarkerConfig.getTemplate(templateFilename);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, objectToBeProcessed);
    }
}
