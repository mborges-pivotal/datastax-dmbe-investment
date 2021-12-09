package com.datastax.dmbe.astra.investment.backend.loader;

import java.io.Reader;
import java.util.List;

import com.datastax.dmbe.astra.investment.backend.model.Trade;

public interface Loader {

    List<Trade> mapFile(String account, Reader reader) throws Exception;
    
}
