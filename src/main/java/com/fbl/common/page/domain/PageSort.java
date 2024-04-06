package com.fbl.common.page.domain;

import com.fbl.common.enums.TextEnum;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Apr 06, 2024
 */
public enum PageSort implements TextEnum {
    ASC("ASC"), DESC("DESC");

    private String textId;

    PageSort(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
