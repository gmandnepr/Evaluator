package com.gman.evaluator.engine;

import com.gman.evaluator.engine.parameters.Counter;
import com.gman.evaluator.engine.parameters.StringSet;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author gman
 * @since 11/27/12 1:24 PM
 */
public class UrlGeneratorTest {

    @Test
    public void testWithoutParams() {

        final UrlGenerator urlGenerator = new UrlGenerator("http://www.site.com/?i=0&j=1&k=2");

        final Set<String> expected = new HashSet<String>(Arrays.asList(
                "http://www.site.com/?i=0&j=1&k=2"));

        for (final String url : urlGenerator) {
            assertTrue(expected.remove(url));
        }
        assertEquals(0, expected.size());
        assertEquals("site.com", urlGenerator.getSite());
    }

    @Test
    public void testWithParams() {

        final UrlGenerator urlGenerator = new UrlGenerator("http://www.site.com/?i=${i}&j=${j}&k=${k}",
                Arrays.<Parameter<?>>asList(new Counter("k", 0, 2), new Counter("i", 0, 1),
                        new Counter("j", 0, 1), new Counter("x", 0, 10)));

        final Set<String> expected = new HashSet<String>(Arrays.asList(
                "http://www.site.com/?i=0&j=0&k=0",
                "http://www.site.com/?i=0&j=0&k=1",
                "http://www.site.com/?i=0&j=0&k=2",

                "http://www.site.com/?i=0&j=1&k=0",
                "http://www.site.com/?i=0&j=1&k=1",
                "http://www.site.com/?i=0&j=1&k=2",

                "http://www.site.com/?i=1&j=0&k=0",
                "http://www.site.com/?i=1&j=0&k=1",
                "http://www.site.com/?i=1&j=0&k=2",

                "http://www.site.com/?i=1&j=1&k=0",
                "http://www.site.com/?i=1&j=1&k=1",
                "http://www.site.com/?i=1&j=1&k=2"));

        for (final String url : urlGenerator) {
            assertTrue(expected.remove(url));
        }
        assertEquals(0, expected.size());
        assertEquals("site.com", urlGenerator.getSite());
    }

    @Test
    public void testWithStringParam() {

        final UrlGenerator urlGenerator = new UrlGenerator("http://www.site.com/?model=${m}&p=${p}",
                Arrays.<Parameter<?>>asList(new Counter("p", 0, 2),
                        new StringSet("m", new HashSet<String>(Arrays.asList("a", "b")))));

        final Set<String> expected = new HashSet<String>(Arrays.asList(
                "http://www.site.com/?model=a&p=0",
                "http://www.site.com/?model=a&p=1",
                "http://www.site.com/?model=a&p=2",

                "http://www.site.com/?model=b&p=0",
                "http://www.site.com/?model=b&p=1",
                "http://www.site.com/?model=b&p=2"));

        for (final String url : urlGenerator) {
            assertTrue(expected.remove(url));
        }
        assertEquals(0, expected.size());
        assertEquals("site.com", urlGenerator.getSite());
    }

    @Test(expected = IllegalStateException.class)
    public void testMissingParamDefinition() {

        new UrlGenerator("http://www.site.com/?i=${i}&j=${j}&k=${k}",
                Arrays.<Parameter<?>>asList(new Counter("k", 0, 2), new Counter("j", 0, 1)));
    }

}
