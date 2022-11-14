/*
 * Copyright (c) 2022 WSO2 LLC. (http://www.wso2.com) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.xlibb.mustache;

import io.ballerina.runtime.api.Environment;
import io.ballerina.runtime.api.Module;
import io.ballerina.runtime.api.TypeTags;
import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BFunctionPointer;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.api.values.BValue;

/**
 * Utility functions of mustache module.
 *
 * @since 0.1.0
 */
public class Utils {
    public static final String ERROR = "Error";
    private static Module mustacheModule;

    public static Module getModule() {
        return mustacheModule;
    }

    @SuppressWarnings("unused")
    public static void setModule(Environment env) {
        mustacheModule = env.getCurrentModule();
    }

    public static BError createError(String message) {
        return ErrorCreator.createError(getModule(), ERROR, StringUtils.fromString(message), null, null);
    }

    /**
     * This function converts the ballerina value to the value required by mustache engine.
     */
    public static Object convert(Object value, JsonMap jsonMap) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return value;
        }
        if (value instanceof Number) {
            return value.toString();
        }
        BValue bValue = (BValue) value;
        if (bValue.getType().getTag() == TypeTags.STRING_TAG) {
            return ((BString) bValue).getValue();
        }
        if (bValue.getType().getTag() == TypeTags.ARRAY_TAG) {
            return new BallerinaArrayIterator((BArray) value, jsonMap);
        }
        if (bValue.getType().getTag() == TypeTags.FUNCTION_POINTER_TAG) {
            return new BallerinaLambda((BFunctionPointer) bValue, jsonMap);
        }
        @SuppressWarnings("unchecked")
        BMap<BString, Object> bMap = (BMap<BString, Object>) value;
        return new JsonMap(bMap, jsonMap.getStrandMetadata());
    }
}
