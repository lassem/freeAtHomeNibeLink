xquery version "3.0";

(:~
: User: lasse
: Date: 19/11/2016
: Time: 18:15
: To change this template use File | Settings | File Templates.
:)

module namespace devicechannels = "devicechannels";

declare function test() {
    for $channel in doc('src/test/resources/getall.xml')//project//devices//device//channel return $channel
};


declare function a() {
    let $d := doc('src/test/resources/getall.xml')

    for $device in $d//project//devices//device
    for $channel in $device//channel
    let $name := $d//strings//string[@nameId = $channel/@nameId]/string()
    let $displayname := $channel//attribute[@name = "displayName"]/string()
    return ($channel/@cid, $name, $displayname, "&#10;")


};

declare function b() {
    let $d := doc('src/test/resources/getall.xml')




    return ($d//strings//string[@nameId = '0001'])
};

declare function c() {
    let $d := doc('src/test/resources/getall.xml')

    for $channel in $d//project//devices//device//channel
    let $displayname := $channel//attribute[@name = "displayName"]/string()
    return ($displayname, "&#10;")


};