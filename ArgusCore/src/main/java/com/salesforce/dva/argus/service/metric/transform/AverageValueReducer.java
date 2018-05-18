/*
 * Copyright (c) 2016, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of Salesforce.com nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
	 
package com.salesforce.dva.argus.service.metric.transform;

import java.util.List;

import com.salesforce.dva.argus.service.tsdb.MetricScanner;
import com.salesforce.dva.argus.system.SystemAssert;

/**
 * Calculates the average of all values at each timestamp across all the metrics.
 *
 * @author  Ruofan Zhang (rzhang@salesforce.com)
 */
public class AverageValueReducer implements ValueReducer {

    //~ Methods **************************************************************************************************************************************

    @Override
    public Double reduce(List<Double> values) {
    	SystemAssert.requireArgument(values.size() != 0, "There must be values to reduce.");
        Double sum = 0.0;

        for (Double value : values) {
            if (value == null) {
                continue;
            }
            sum += value;
        }
        return (sum / values.size());
    }
    
    @Override
    public Double reduceScanner(MetricScanner scanner) {
    	SystemAssert.requireArgument(scanner.hasNextDP(), "There must be datapoints to reduce.");
    		Double sum = 0.0;
    		int dpCount = 0;
    		
    		while (scanner.hasNextDP()) {
    			dpCount++;
    			Double val = scanner.getNextDP().getValue();
    			if (val != null) {
    				sum += val;
    			}
    		}
    		
    		return (sum / dpCount);
    }
    
    @Override
    public String name() {
        return TransformFactory.Function.AVERAGE.name();
    }
}
/* Copyright (c) 2016, Salesforce.com, Inc.  All rights reserved. */
