import ballerina/io;
import ballerina/test;

@test:Config {}
isolated function testCompileMethod() returns error? {
    var bold = isolated function(string input, Render render) returns string {
        return string `<b> ${input} </b>`;
    };
    Data data = {person: {name : "sabthar"}, text: bold};
    string html = check compile("tests/todo.mustache", data);
    io:println(html);
}
