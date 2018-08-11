package com.stevesouza.resttemplate;

import com.stevesouza.resttemplate.utils.MiscUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class TestUtils {

    public static void assertLenientJsonEquality(Object actual, Object expected) {
        try {
            String actualJson = MiscUtils.toJsonString(actual);
            String expectedJson = MiscUtils.toJsonString(expected);
            // this is a deeper test of the full web of objects being equal.  They are being
            // tested as json as this test is possible in json format.  If they were pojos the
            // test for equality would fail as the vo has fewer fields than the entity. If the following json documents
            // were compared they would be considered equal.
            //  entity: {"name":"steve", "salary":50, "favoriteNumbers":[1,2,3,4]}
            //  vo: {"name":"steve", "salary":50}
            JSONAssert.assertEquals("Values of fields that exist in both json documents do not match.", actualJson, expectedJson, JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
