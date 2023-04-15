/**
 * Copyright of awana App. All rights reserved.
 */
package com.awana.common.search;

import java.util.List;

public interface SearchFieldParams<T extends SearchField> {
    public List<SearchField> getSearchFields();
}
