package gov.va.vinci.ef.ae;

/*
 * #%L
 * Echo concept exctractor
 * %%
 * Copyright (C) 2010 - 2016 Department of Veterans Affairs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Validates Ejection Fraction numeric measurement range.
 *
 * Created by thomasginter on 9/1/15.
 */
public class EFValidatorAnnotator extends NumericValidatorAnnotator {
    /**
     * Check for a value between either 10 and 90 or 0.1 and 0.9.
     *
     * @param value Double value to be validated
     * @return true if the value is in the ranges
     */
    @Override
    protected boolean isValid(double value) {
        if((value >= 10 && value <= 90) ||
            (value >= 0.1 && value <= 0.9))
            return true;
        return false;
    }


}
