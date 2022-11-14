package io.ballerina.xlibb.mustache;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateFunction;
import io.ballerina.runtime.api.values.BFunctionPointer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * This class implements mustache lambda.
 */
public class BallerinaLambda implements TemplateFunction {
    private final BFunctionPointer<Object, Object> functionPointer;
    private final JsonMap scopes;

    public BallerinaLambda(BFunctionPointer<Object, Object> functionPointer, JsonMap scopes) {
        this.functionPointer = functionPointer;
        this.scopes = scopes;
    }

    public String compile(String input) throws IOException {
        Mustache mustache = BallerinaMustache.FACTORY.compile(new StringReader(input), "");
        StringWriter out = new StringWriter();
        mustache.execute(out, scopes);
        out.close();
        return out.toString();
    }

    @Override
    public String apply(String s) {
        // try {
            // BString arg = StringUtils.fromString(compile(s));
            // BString res =  functionPointer.call(arg);
            // return res.getValue();
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
        return s;
    }
}
