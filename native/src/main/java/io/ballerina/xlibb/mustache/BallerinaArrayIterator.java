package io.ballerina.xlibb.mustache;

import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BIterator;

import java.util.Iterator;

/**
 * This class converts ballerina array values to the values required by mustache.
 */
public class BallerinaArrayIterator implements Iterator {
    private final BIterator<?> iterator;
    private final JsonMap scopes;

    public BallerinaArrayIterator(BArray bArray, JsonMap scopes) {
        this.iterator = bArray.getIterator();
        this.scopes = scopes;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        return Utils.convert(iterator.next(), scopes);
    }

    @Override
    public void remove() {
    }
}
