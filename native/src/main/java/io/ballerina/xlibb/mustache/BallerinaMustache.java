package io.ballerina.xlibb.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheNotFoundException;
import io.ballerina.runtime.api.Environment;
import io.ballerina.runtime.api.async.StrandMetadata;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.io.IOException;
import java.io.StringWriter;


/**
 * Provides native function implementation of mustache.
 */
public class BallerinaMustache {
    public static final DefaultMustacheFactory FACTORY = new DefaultMustacheFactory();

    @SuppressWarnings("unused")
    public static Object compile(Environment env, BString fileName, BMap<BString, Object> json) {
        StringWriter stringWriter = new StringWriter();
        StrandMetadata strandMetadata = env.getStrandMetadata();
        try {
            Mustache mustache = FACTORY.compile(fileName.getValue());
            mustache.execute(stringWriter, new JsonMap(json, strandMetadata)).close();
        } catch (IOException | MustacheNotFoundException e) {
            return Utils.createError(e.getMessage());
        }
        return StringUtils.fromString(stringWriter.toString());
    }
}
