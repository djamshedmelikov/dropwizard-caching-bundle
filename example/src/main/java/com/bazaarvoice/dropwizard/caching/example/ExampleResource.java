package com.bazaarvoice.dropwizard.caching.example;

import com.bazaarvoice.dropwizard.caching.CacheGroup;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.sun.jersey.api.core.HttpContext;
import io.dropwizard.jersey.caching.CacheControl;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
public class ExampleResource {
    private int _count = 1;

    @GET
    @Path("/test")
    @CacheControl(maxAge = 60)
    @CacheGroup("otter")
    public ExampleResult getTestData(@Context HttpContext requestContext) {
//        throw new RuntimeException("uh oh");
        return new ExampleResult(_count++);
    }

    @GET
    @Path("/test2")
    public ExampleResult doIt() {
        return new ExampleResult(_count * 2);
    }

    @POST
    @Path("/test2")
    @CacheGroup("otterpop")
    public List<ExampleResult> postIt(List<Integer> values) {
        return FluentIterable
                .from(values)
                .transform(new Function<Integer, ExampleResult>() {
                    public ExampleResult apply(Integer input) {
                        return new ExampleResult(input);
                    }
                })
                .toList();
    }

    public static class ExampleResult {
        public int resultValue = 100;

        public ExampleResult(int value) {
            resultValue = value;
        }
    }
}
