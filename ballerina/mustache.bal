import ballerina/jballerina.java;

public type Lambda isolated function(string input, Render render) returns string;

public type Render isolated function(string input) returns string;

public type Data map<json|Lambda>|map<Data>;

public isolated function compile(string fileName, Data 'json) returns string|Error = @java:Method {
    'class: "io.ballerina.xlibb.mustache.BallerinaMustache"
} external;
