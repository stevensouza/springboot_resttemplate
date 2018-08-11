package com.stevesouza.resttemplate;

import com.stevesouza.resttemplate.utils.MiscUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class TestUtils {

    /**
     *
     *   This is a deeper test of the full web of objects being equal.  They are being
     *   tested as json as this test is possible in json format.  If they were pojos the
     *   test for equality would fail as the vo has fewer fields than the entity. If the following json documents
     *   were compared they would be considered equal.
     *     actual: {"name":"steve", "salary":50}
     *     expected: {"name":"steve", "salary":50, "favoriteNumbers":[1,2,3,4]}
     *
     *   if they are flipped then an exception would be thrown.
     *
     * @param actual must have as many or fewer fields than the expected json: {"name":"steve", "salary":50}
     * @param expected {"name":"steve", "salary":50, "favoriteNumbers":[1,2,3,4]}
     */

    public static void assertLenientJsonEquality(Object actual, Object expected) {
        try {
            String actualJson = MiscUtils.toJsonString(actual);
            String expectedJson = MiscUtils.toJsonString(expected);
            JSONAssert.assertEquals("Values of fields that exist in both json documents do not match.", actualJson, expectedJson, JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
